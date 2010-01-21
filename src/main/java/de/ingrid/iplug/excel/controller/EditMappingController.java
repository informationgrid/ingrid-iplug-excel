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
	public EditMappingController(final SheetsService sheetsService) {
		_sheetsService = sheetsService;
	}

    @RequestMapping(value = "/iplug-pages/editMapping.html", method = RequestMethod.GET)
	public String editSheet(
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) final int sheetIndex,
			final Model model) throws IOException {
		final Sheets sheets = new Sheets();
		final Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		final Iterator<Sheet> iterator = savedSheets.getSheets().iterator();
		while (iterator.hasNext()) {
			final Sheet sheet = iterator.next();
			if (sheet.getSheetIndex() == sheetIndex) {
				final String fileName = sheet.getFileName();
				final Values values = loadValues(sheetIndex, fileName,
						plugdescriptionCommandObject);
				sheet.setValues(values);
				sheets.addSheet(sheet);
				break;
			}
		}
		model.addAttribute("sheets", sheets);
        return "redirect:/iplug-pages/mapping.html";
	}

	private Values loadValues(final int sheetIndex, final String fileName,
			final PlugdescriptionCommandObject plugdescriptionCommandObject)
			throws IOException {
		final File workinDirectory = plugdescriptionCommandObject
				.getWorkinDirectory();
		final File mappingDir = new File(workinDirectory, "mapping");
		final File file = new File(mappingDir, fileName);
		final Sheets sheets = _sheetsService.createSheets(file);
		return sheets.getSheets().get(sheetIndex).getValues();
	}
}
