package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.mapping.FieldType;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class AddToIndexController {

    @RequestMapping(value = "/iplug-pages/addToIndex.html", method = RequestMethod.GET)
	public String addToIndex(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final String type,
			@RequestParam(required = true) final int index,
			@RequestParam(required = true) final String label, final ModelMap model) {

		model.addAttribute("type", type);
		model.addAttribute("index", index);
		model.addAttribute("label", label);
		model.addAttribute("fieldTypes", FieldType.values());
        return "/iplug-pages/addToIndex";
	}

    @RequestMapping(value = "/iplug-pages/addToIndex.html", method = RequestMethod.POST)
	public String addToIndexPost(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index,
			@RequestParam(required = false) final String fieldName,
			@RequestParam(required = false) final String ownFieldName,
			@RequestParam(required = true) final String fieldType,
			@RequestParam(required = true) final float rank) {

		final Sheet sheet = sheets.getSheets().get(0);
		final DocumentType documentType = sheet.getDocumentType();
		AbstractEntry entry = null;
		switch (documentType) {
		case ROW:
			entry = sheet.getColumns().get(index);
			break;
		case COLUMN:
			entry = sheet.getRows().get(index);
			break;
		default:
			break;
		}
		final String label = !"".equals(ownFieldName) ? ownFieldName : fieldName;
		entry.setLabel(label);
		entry.setMapped(true);
		entry.setRank(rank);
		entry.setFieldType(FieldType.valueOf(fieldType));

        return "redirect:/iplug-pages/mapping.html";
	}

    @RequestMapping(value = "/iplug-pages/removeFromIndex.html", method = RequestMethod.GET)
	public String removeFromIndex(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index) {

		final Sheet sheet = sheets.getSheets().get(0);
		final DocumentType documentType = sheet.getDocumentType();
		AbstractEntry entry = null;
		String label = null;
		switch (documentType) {
		case ROW:
			entry = sheet.getColumns().get(index);
			label = ((Column) entry).getDefaultLabel();
			break;
		case COLUMN:
			entry = sheet.getRows().get(index);
			label = ((Row) entry).getIndex() + "";
			break;
		default:
			break;
		}
		entry.getFilters().clear();
		entry.setMapped(false);
		entry.setRank(0);
		entry.setFieldType(FieldType.TEXT);
		entry.setLabel(label);

        return "redirect:/iplug-pages/mapping.html";
	}
}
