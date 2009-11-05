package de.ingrid.iplug.excel.controller;

import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes( { "plugDescription" })
public class DeleteMappingController {

	@RequestMapping(value = "/iplug/deleteMapping.html", method = RequestMethod.POST)
	public String deleteMapping(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject plugdescriptionCommandObject,
			@RequestParam(value = "sheetIndex", required = true) int sheetIndex) {
		Sheets sheets = (Sheets) plugdescriptionCommandObject.get("sheets");
		Iterator<Sheet> iterator = sheets.getSheets().iterator();
		while (iterator.hasNext()) {
			Sheet sheet = iterator.next();
			if (sheet.getSheetIndex() == sheetIndex) {
				iterator.remove();
				break;
			}
		}
		return "redirect:/base/save.html";
	}
}
