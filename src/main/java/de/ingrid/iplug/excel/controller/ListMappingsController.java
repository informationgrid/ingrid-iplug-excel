package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;

@Controller
@SessionAttributes("plugDescription")
public class ListMappingsController {

	@RequestMapping(value = "/iplug/listMappings.html", method = RequestMethod.GET)
	public String listMappings(@ModelAttribute("plugDescription") final PlugdescriptionCommandObject commandObject, ModelMap model) {
		return "/iplug/listMappings";
	}

	

}