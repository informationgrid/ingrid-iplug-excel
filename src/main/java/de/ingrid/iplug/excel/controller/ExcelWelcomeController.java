package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;


@Controller
@RequestMapping(value = "/iplug/welcome.html")
@SessionAttributes("plugDescription")
public class ExcelWelcomeController {

	@RequestMapping(method = RequestMethod.GET)
	public String welcome(
			@ModelAttribute("plugDescription") PlugdescriptionCommandObject commandObject) {
		return "redirect:/iplug/listMappings.html";
	}

}
