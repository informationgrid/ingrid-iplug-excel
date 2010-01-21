package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class MappingController {

    @RequestMapping(value = "/iplug-pages/mapping.html", method = RequestMethod.GET)
	public String mapping(@ModelAttribute("sheets") final Sheets sheets, @RequestParam(required=false) Integer start,
			@RequestParam(required=false) Integer hitsPerPage, final ModelMap model) {

		final Sheet sheet = sheets.getSheets().get(0);
		final int maxHits = sheet.getRows().size();
		if(start == null || start < 0){
			start = 0;
		}

		if(hitsPerPage == null){
			hitsPerPage = 10;
		}

		if (start > maxHits) {
			start = maxHits;
		}

		final int pageStart = (start / hitsPerPage) * hitsPerPage;

		int prevStart = pageStart - hitsPerPage;
		if(prevStart < 0){
			prevStart = 0;
		}

		final int nextStart = pageStart + hitsPerPage;

		model.addAttribute("start", pageStart);
		model.addAttribute("maxHits", maxHits);
		model.addAttribute("hitsPerPage", hitsPerPage);
		model.addAttribute("prevStart", prevStart);
		model.addAttribute("nextStart", nextStart);

        return "/iplug-pages/mapping";
	}

}
