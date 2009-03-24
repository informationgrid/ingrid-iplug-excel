package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/iplug/mapping.html")
@SessionAttributes("tableListCommand")
public class MappingController {

	@RequestMapping(method = RequestMethod.GET)
	public String mapping(
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand) {
		return "/iplug/mapping";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postMapping(
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand,
			@RequestParam(value = "finish", required = false) Object finish) {
		if (finish != null) {
			return "redirect:/iplug/finish.html";
		}
		return "redirect:/iplug/mapping.html";
	}

}
