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
	public AddFilterController(AddToIndexFilter filter) {
		_filter = filter;
	}

	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.GET)
	public String addFilter(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final String label, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("index", index);
		model.addAttribute("label", label);
		List<FilterType> filterTypes = new ArrayList<FilterType>();
		Sheet sheet = sheets.getSheets().get(0);
		SheetService sheetService = new SheetService();
		DocumentType documentType = sheet.getDocumentType();
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
		
		FieldType fieldType = doc.getFieldType();
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
		return "/iplug/addFilter";
	}

	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.POST)
	public String addFilterPost(
			@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int index,
			@RequestParam(value = "filterType", required = true) final String filterTypeString,
			@RequestParam(required = true) final String expression) {

		FilterType filterType = FilterType.valueOf(filterTypeString);
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

		Sheet sheet = sheets.getSheets().get(0);
		DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
			Column col = sheet.getColumns().get(index);
			col.addFilter(filter);
			BitSet bitSet = _filter.filterRows(sheet);
			List<Row> rows = sheet.getRows();
			for (Row row : rows) {
				row.setMatchFilter(!bitSet.get(row.getIndex()));
			}
			break;
		case COLUMN:
			Row row = sheet.getRows().get(index);
			row.addFilter(filter);
			BitSet columnSet = _filter.filterColumns(sheet);
			List<Column> columns = sheet.getColumns();
			for (Column column : columns) {
				column.setMatchFilter(!columnSet.get(column.getIndex()));
			}
			break;
		default:
			break;
		}

		return "redirect:/iplug/mapping.html";
	}

	@RequestMapping(value = "/iplug/removeFilter.html", method = RequestMethod.GET)
	public String removeFilter(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final int filterIndex) {

		Sheet sheet = sheets.getSheets().get(0);
		DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
			Column col = sheet.getColumns().get(index);
			col.removeFilter(filterIndex);
			BitSet bitSet = _filter.filterRows(sheet);
			List<Row> rows = sheet.getRows();
			for (Row row : rows) {
				row.setMatchFilter(!bitSet.get(row.getIndex()));
			}
			break;
		case COLUMN:
			Row row = sheet.getRows().get(index);
			row.removeFilter(filterIndex);
			BitSet columnSet = _filter.filterColumns(sheet);
			List<Column> columns = sheet.getColumns();
			for (Column column : columns) {
				column.setMatchFilter(!columnSet.get(column.getIndex()));
			}
			break;
		default:
			break;
		}


		return "redirect:/iplug/mapping.html";
	}
}
