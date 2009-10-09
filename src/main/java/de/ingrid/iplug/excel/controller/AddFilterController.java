package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class AddFilterController {
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.GET)
	public String addFilter(@ModelAttribute("sheets") Sheets sheets) {
		// this is still a dummy controller
		return "/iplug/addFilter";
	}
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.POST)
	public String addFilterPost(@ModelAttribute("sheets") Sheets sheets) {
		// this is still a dummy controller
		return "redirect:/iplug/mapping.html";
	}
}
