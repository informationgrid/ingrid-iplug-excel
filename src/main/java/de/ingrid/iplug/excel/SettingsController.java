package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class SettingsController {

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.GET)
	public String settings(ModelMap model,
			@ModelAttribute("sheets") Sheets sheets) {
		
		model.addAttribute("documentTypes", DocumentType.values());
		return "/iplug/settings";
	}

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.POST)
	public String postSettings(@ModelAttribute("sheets") Sheets sheets){
		System.out.println("SettingsController.postSettings() size: " +sheets.getSheets().size());
		System.out.println("SettingsController.postSettings() desc: " +sheets.getSheets().get(0).getDescription());
		System.out.println("SettingsController.postSettings() isFirstIsLabel: " +sheets.getSheets().get(0).isFirstIsLabel());
		System.out.println("SettingsController.postSettings() documentType: " +sheets.getSheets().get(0).getDocumentType());

		
		return "redirect:/iplug/mapping.html";
	}

}
