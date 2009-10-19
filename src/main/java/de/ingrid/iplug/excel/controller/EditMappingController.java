package de.ingrid.iplug.excel.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;
import de.ingrid.iplug.excel.service.SheetsService;

@Controller
@SessionAttributes( { "plugDescription", "sheets" })
public class EditMappingController {

	private final SheetsService _sheetsService;

	@Autowired
	public EditMappingController(SheetsService sheetsService) {
		_sheetsService = sheetsService;
	}

	@RequestMapping(value = "/iplug/editMapping.html", method = RequestMethod.GET)
	public String editSheet(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) int sheetIndex,
			Model model) throws IOException {
		Sheets sheets = new Sheets();
		Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		Iterator<Sheet> iterator = savedSheets.getSheets().iterator();
		while (iterator.hasNext()) {
			Sheet sheet = iterator.next();
			if (sheet.getSheetIndex() == sheetIndex) {
				String fileName = sheet.getFileName();
				Values values = loadValues(sheetIndex, fileName,
						plugdescriptionCommandObject);
				sheet.setValues(values);
				sheets.addSheet(sheet);
				break;
			}
		}
		model.addAttribute("sheets", sheets);
		return "redirect:/iplug/mapping.html";
	}

	private Values loadValues(int sheetIndex, String fileName,
			PlugdescriptionCommandObject plugdescriptionCommandObject)
			throws IOException {
		File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		File file = new File(workinDirectory, fileName);
		Sheets sheets = _sheetsService.createSheets(file);
		return sheets.getSheets().get(sheetIndex).getValues();
	}
}
