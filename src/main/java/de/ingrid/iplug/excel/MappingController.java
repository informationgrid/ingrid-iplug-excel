package de.ingrid.iplug.excel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class MappingController {

	@RequestMapping(value = "/iplug/mapping.html", method = RequestMethod.GET)
	public String mapping(@ModelAttribute("sheets") Sheets sheets) {
		return "/iplug/mapping";
	}

}
