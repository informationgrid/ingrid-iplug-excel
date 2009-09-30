package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ListMappingsController {

	@RequestMapping(value = "/iplug/listMappings.html", method = RequestMethod.GET)
	public String listMappings() {
		// this is still a dummy controller
		return "/iplug/listMappings";
	}

	

}