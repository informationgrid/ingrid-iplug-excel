package de.ingrid.iplug.excel;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("tableListCommand")
public class MappingController {

	@RequestMapping(value = "/iplug/mapping.html", method = RequestMethod.GET)
	public String mapping(
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand) {
		return "/iplug/mapping";
	}

	@RequestMapping(value = "/iplug/mapping.html", method = RequestMethod.POST)
	public String postMapping(
			@RequestParam("table") Integer table,
			@RequestParam("column") Integer column,
			@RequestParam("val") String val,
			@ModelAttribute("tableListCommand") TableListCommand tableListCommand) {
		TableCommand tableCommand = tableListCommand.getTableCommands().get(
				table);
		List<String> headers = tableCommand.getHead().getHeaders();
		headers.set(column, val);
		return "redirect:/iplug/mapping.html";
	}

}
