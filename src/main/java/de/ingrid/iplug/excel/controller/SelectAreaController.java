package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("sheets")
public class SelectAreaController {

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.GET)
	public String selectArea() {
		// this is still a dummy controller
		return "/iplug/selectArea";
	}
	
	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.POST)
	public String subitSelectArea() {
		// this is still a dummy controller
		return "redirect:/iplug/mapping.html";
	}

	

}