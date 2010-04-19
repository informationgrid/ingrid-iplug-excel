package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;


/**
 * Controller for welcome view.
 *
 */
@Controller
@RequestMapping(value = "/iplug-pages/welcome.html")
@SessionAttributes("plugDescription")
public class ExcelWelcomeController {

	/**
	 * Redirect to listMapping.html.	
	 * 
	 * @param commandObject
	 * @return
	 * 		Web request "redirect:/iplug-pages/listMappings.html"
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String welcome(
			@ModelAttribute("plugDescription") final PlugdescriptionCommandObject commandObject) {
        return "redirect:/iplug-pages/listMappings.html";
	}

}
