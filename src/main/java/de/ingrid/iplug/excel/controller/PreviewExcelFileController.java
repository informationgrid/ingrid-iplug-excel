package de.ingrid.iplug.excel.controller;

import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class PreviewExcelFileController {

	@RequestMapping(value = "/iplug/previewExcelFile.html", method = RequestMethod.GET)
	public String settings(ModelMap model,
			@ModelAttribute("sheets") Sheets sheets) {
		
		return "/iplug/previewExcelFile";
	}

	@RequestMapping(value = "/iplug/previewExcelFile.html", method = RequestMethod.POST)
	public String postSettings(@ModelAttribute("sheets") Sheets sheets, @RequestParam(required=true) final int sheetIndex){
		
		Iterator<Sheet> iterator = sheets.getSheets().iterator();
		while (iterator.hasNext()) {
			Sheet next = iterator.next();
			if (next.getSheetIndex() != sheetIndex) {
				iterator.remove();
			}
		}
		return "redirect:/iplug/settings.html";
	}

}
