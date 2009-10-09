package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.FieldType;
import de.ingrid.iplug.excel.model.Filter;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class AddToIndexController {

	private final SheetService _sheetService;

	@Autowired
	public AddToIndexController(SheetService sheetService) {
		_sheetService = sheetService;
	}

	@RequestMapping(value = "/iplug/addToIndex.html", method = RequestMethod.GET)
	public String addToIndex(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final String label, ModelMap model) {

		model.addAttribute("type", type);
		model.addAttribute("index", index);
		model.addAttribute("label", label);
		model.addAttribute("fieldTypes", FieldType.values());
		return "/iplug/addToIndex";
	}

	@RequestMapping(value = "/iplug/addToIndex.html", method = RequestMethod.POST)
	public String addToIndexPost(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index,
			@RequestParam(required = false) final String fieldName,
			@RequestParam(required = false) final String ownFieldName,
			@RequestParam(required = true) final String fieldType,
			@RequestParam(required = true) final float rank) {

		if (type.equals("col")) {
			Column column = _sheetService.getColumnByIndex(sheets.getSheets()
					.get(0), index);
			if (column != null) {
				if (!"".equals(ownFieldName)) {
					column.setLabel(ownFieldName);
				} else {
					column.setLabel(fieldName);
				}

				column.setMapped(true);
				column.setRank(rank);
				column.setFieldType(FieldType.valueOf(fieldType));
			}
		} else if (type.equals("row")) {
			Row row = _sheetService.getRowByIndex(sheets.getSheets().get(0),
					index);
			if (row != null) {
				if (!"".equals(ownFieldName)) {
					row.setLabel(ownFieldName);
				} else {
					row.setLabel(fieldName);
				}

				row.setMapped(true);
				row.setRank(rank);
				row.setFieldType(FieldType.valueOf(fieldType));
			}
		}

		return "redirect:/iplug/mapping.html";
	}

	@RequestMapping(value = "/iplug/removeFromIndex.html", method = RequestMethod.GET)
	public String removeFromIndex(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index) {
		if (type.equals("col")) {
			Column column = _sheetService.getColumnByIndex(sheets.getSheets()
					.get(0), index);
			// remove filters first
			List<Filter> filters = column.getFilters();
			Iterator<Filter> iterator = filters.iterator();
			while (iterator.hasNext()) {
				iterator.next();
				iterator.remove();
			}

			column.setMapped(false);
			column.setRank(0);
			column.setFieldType(null);
			column.setLabel(column.getDefaultLabel());
		} else if (type.equals("row")) {
			Row row = _sheetService.getRowByIndex(sheets.getSheets().get(0),
					index);
			// remove filters first
			List<Filter> filters = row.getFilters();
			Iterator<Filter> iterator = filters.iterator();
			while (iterator.hasNext()) {
				iterator.next();
				iterator.remove();
			}

			row.setMapped(false);
			row.setRank(0);
			row.setFieldType(null);
			row.setLabel((row.getIndex() + 1) + "");
		}
		return "redirect:/iplug/mapping.html";
	}
}
