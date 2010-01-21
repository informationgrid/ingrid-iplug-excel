package de.ingrid.iplug.excel.controller;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.AddToIndexFilter;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class AddFilterController {

	private final AddToIndexFilter _filter;

	@Autowired
	public AddFilterController(final AddToIndexFilter filter) {
		_filter = filter;
	}

    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.GET)
	public String addFilter(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final String label, final ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("index", index);
		model.addAttribute("label", label);
		final List<FilterType> filterTypes = new ArrayList<FilterType>();
		final Sheet sheet = sheets.getSheets().get(0);
		final SheetService sheetService = new SheetService();
		final DocumentType documentType = sheet.getDocumentType();
		AbstractEntry doc = null;
		switch (documentType) {
		case ROW:
			 doc = sheetService.getColumnByIndex(sheet, index);
			break;
		case COLUMN:
			doc = sheetService.getRowByIndex(sheet, index);
			break;
		default:
			break;
		}

		final FieldType fieldType = doc.getFieldType();
		switch (fieldType) {
		case BOOLEAN:
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			break;
		case DATE:
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


		model.addAttribute("filterTypes", filterTypes);
        return "/iplug-pages/addFilter";
	}

    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.POST)
	public String addFilterPost(
			@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index,
			@RequestParam(value = "filterType", required = true) final String filterTypeString,
			@RequestParam(required = true) final String expression) {

		final FilterType filterType = FilterType.valueOf(filterTypeString);
		Filter filter = null;
		switch (filterType) {
		case GREATER_THAN:
		case LOWER_THAN:
			filter = new Filter(Double.parseDouble(expression), filterType);
			break;
		case CONTAINS:
		case NOT_CONTAINS:
		case EQUAL:
		case NOT_EQUAL:
		case BEFORE:
		case AFTER:
			filter = new Filter(expression, filterType);
			break;

		default:
			break;
		}

		final Sheet sheet = sheets.getSheets().get(0);
		final DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
			final Column col = sheet.getColumns().get(index);
			col.addFilter(filter);
			final BitSet bitSet = _filter.filterRows(sheet);
			final List<Row> rows = sheet.getRows();
			for (final Row row : rows) {
				row.setMatchFilter(!bitSet.get(row.getIndex()));
			}
			break;
		case COLUMN:
			final Row row = sheet.getRows().get(index);
			row.addFilter(filter);
			final BitSet columnSet = _filter.filterColumns(sheet);
			final List<Column> columns = sheet.getColumns();
			for (final Column column : columns) {
				column.setMatchFilter(!columnSet.get(column.getIndex()));
			}
			break;
		default:
			break;
		}

        return "redirect:/iplug-pages/mapping.html";
	}

    @RequestMapping(value = "/iplug-pages/removeFilter.html", method = RequestMethod.GET)
	public String removeFilter(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final int filterIndex) {

		final Sheet sheet = sheets.getSheets().get(0);
		final DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
			final Column col = sheet.getColumns().get(index);
			col.removeFilter(filterIndex);
			final BitSet bitSet = _filter.filterRows(sheet);
			final List<Row> rows = sheet.getRows();
			for (final Row row : rows) {
				row.setMatchFilter(!bitSet.get(row.getIndex()));
			}
			break;
		case COLUMN:
			final Row row = sheet.getRows().get(index);
			row.removeFilter(filterIndex);
			final BitSet columnSet = _filter.filterColumns(sheet);
			final List<Column> columns = sheet.getColumns();
			for (final Column column : columns) {
				column.setMatchFilter(!columnSet.get(column.getIndex()));
			}
			break;
		default:
			break;
		}


        return "redirect:/iplug-pages/mapping.html";
	}
}
