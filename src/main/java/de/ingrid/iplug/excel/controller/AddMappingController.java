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
import de.ingrid.iplug.excel.service.EmptySheetFilter;
import de.ingrid.iplug.excel.service.SheetsService;

@Controller
@SessionAttributes( { "plugDescription", "sheets" })
public class AddMappingController {

	private final SheetsService _sheetsService;
	private final EmptySheetFilter _excludeFilter;

	@Autowired
	public AddMappingController(final SheetsService sheetsService,
			final EmptySheetFilter excludeFilter) {
		_sheetsService = sheetsService;
		_excludeFilter = excludeFilter;
	}

    @RequestMapping(value = "/iplug-pages/addMapping.html", method = RequestMethod.GET)
	public String editSheet(
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) final int sheetIndex,
			final Model model) throws IOException {
		final Sheets sheets = new Sheets();
		final Sheets savedSheets = (Sheets) plugdescriptionCommandObject
				.get("sheets");
		File mappingFile = null;
		Iterator<Sheet> iterator = savedSheets.getSheets().iterator();
		while (iterator.hasNext()) {
			final Sheet sheet = iterator.next();
			if (sheet.getSheetIndex() == sheetIndex) {
				final String fileName = sheet.getFileName();
				final File workinDirectory = plugdescriptionCommandObject
						.getWorkinDirectory();
				mappingFile = new File(new File(workinDirectory, "mapping"),
						fileName);
				break;
			}
		}
		final Sheets sheetsFromHdd = _sheetsService.createSheets(mappingFile);
		iterator = sheetsFromHdd.getSheets().iterator();
		while (iterator.hasNext()) {
			final Sheet sheetFromHdd = iterator.next();
			if (sheetFromHdd.getSheetIndex() != sheetIndex) {
				_excludeFilter.excludeEmtpyRowsAndColumns(sheetFromHdd);
				sheetFromHdd.setFileName(mappingFile.getName());
				sheets.addSheet(sheetFromHdd);
			}
		}

		model.addAttribute("sheets", sheets);
        return "redirect:/iplug-pages/previewExcelFile.html";
	}

}
