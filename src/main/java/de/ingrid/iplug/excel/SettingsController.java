package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("tableListCommand")
public class SettingsController {

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.GET)
	public String settings(
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand) {
		return "/iplug/settings";
	}

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.POST)
	public String postSettings(){
			// todo
		return "redirect:/iplug/mapping.html";
	}

}
