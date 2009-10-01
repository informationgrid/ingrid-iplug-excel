package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddFilterController {
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.GET)
	public String addFilter() {
		// this is still a dummy controller
		return "/iplug/addFilter";
	}
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.POST)
	public String addFilterPost() {
		// this is still a dummy controller
		return "redirect:/iplug/mapping.html";
	}
}
