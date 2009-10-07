package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddToIndexController {
	
	@RequestMapping(value = "/iplug/addToIndex.html", method = RequestMethod.GET)
	public String addToIndex() {
		// this is still a dummy controller
		return "/iplug/addToIndex";
	}
	
	@RequestMapping(value = "/iplug/addToIndex.html", method = RequestMethod.POST)
	public String addToIndexPost() {
		// this is still a dummy controller
		return "redirect:/iplug/mapping.html";
	}
}
