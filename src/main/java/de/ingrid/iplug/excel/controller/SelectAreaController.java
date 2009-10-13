package de.ingrid.iplug.excel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SelectAreaExcludeFilter;

@Controller
@SessionAttributes("sheets")
public class SelectAreaController {

	private final SelectAreaExcludeFilter _selectAreaExcludeFilter;

	@Autowired
	public SelectAreaController(SelectAreaExcludeFilter selectAreaExcludeFilter) {
		_selectAreaExcludeFilter = selectAreaExcludeFilter;
	}

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.GET)
	public String selectArea(@ModelAttribute("sheets") Sheets sheets,
			ModelMap model) {
		Sheet sheet = sheets.getSheets().get(0);
		model.addAttribute("columns", sheet.getColumns());
		model.addAttribute("rows", sheet.getRows());
		return "/iplug/selectArea";
	}

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.POST)
	public String subitSelectArea(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int fromCol,
			@RequestParam(required = true) final int toCol,
			@RequestParam(required = true) final int fromRow,
			@RequestParam(required = true) final int toRow) {

		Sheet sheet = sheets.getSheets().get(0);

		_selectAreaExcludeFilter.excludeNonSelectedArea(sheet, fromCol, toCol,
				fromRow, toRow);

		return "redirect:/iplug/mapping.html";
	}

}