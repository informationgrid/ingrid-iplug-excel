package de.ingrid.iplug.excel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.mapping.Filter;
import de.ingrid.admin.mapping.Filter.FilterType;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.service.SheetFilter;

@Controller
@SessionAttributes("sheet")
public class AddFilterController {

    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.GET)
    public String addFilter(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            final ModelMap modelMap) {
        final DocumentType documentType = sheet.getDocumentType();
        modelMap.addAttribute("type", documentType);
        modelMap.addAttribute("index", index);

		AbstractEntry doc = null;
		switch (documentType) {
		case ROW:
            doc = sheet.getColumn(index);
			break;
		case COLUMN:
            doc = sheet.getRow(index);
			break;
		default:
			break;
		}
        modelMap.addAttribute("label", doc.getLabel());

        final List<FilterType> filterTypes = new ArrayList<FilterType>();
		final FieldType fieldType = doc.getFieldType();
		switch (fieldType) {
		case BOOLEAN:
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			break;
		case DATE:
            filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.BEFORE);
			filterTypes.add(FilterType.AFTER);
			break;
		case KEYWORD:
		case TEXT:
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			filterTypes.add(FilterType.CONTAINS);
			filterTypes.add(FilterType.NOT_CONTAINS);
			break;
		case NUMBER:
			filterTypes.add(FilterType.GREATER_THAN);
			filterTypes.add(FilterType.LOWER_THAN);
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			break;
		default:
			break;
		}

        modelMap.addAttribute("filterTypes", filterTypes);
        return "/iplug-pages/addFilter";
	}

    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.POST)
    public String addFilterPost(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            @RequestParam final FilterType filterType, @RequestParam final String expression) {
		Filter filter = null;
        if (filterType == FilterType.GREATER_THAN || filterType == FilterType.LOWER_THAN) {
            filter = new Filter(Double.parseDouble(expression), filterType);
        } else {
            filter = new Filter(expression, filterType);
		}

        switch (sheet.getDocumentType()) {
		case ROW:
            final Column col = sheet.getColumn(index);
			col.addFilter(filter);
            SheetFilter.filter(sheet, col, filter);
			break;
		case COLUMN:
            final Row row = sheet.getRow(index);
			row.addFilter(filter);
            SheetFilter.filter(sheet, row, filter);
			break;
		default:
			break;
		}

        return "redirect:/iplug-pages/mapping.html";
	}

    @RequestMapping(value = "/iplug-pages/removeFilter.html", method = RequestMethod.GET)
    public String removeFilter(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            @RequestParam final int filterIndex) {
		final DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
            final Column col = sheet.getColumn(index);
			col.removeFilter(filterIndex);
			break;
		case COLUMN:
            final Row row = sheet.getRow(index);
			row.removeFilter(filterIndex);
			break;
		default:
			break;
		}
        SheetFilter.filter(sheet);

        return "redirect:/iplug-pages/mapping.html";
	}
}
