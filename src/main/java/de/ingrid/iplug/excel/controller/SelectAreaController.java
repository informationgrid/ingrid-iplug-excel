package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.service.SheetFilter;

@Controller
@SessionAttributes("sheet")
public class SelectAreaController {

    @RequestMapping(value = "/iplug-pages/selectArea.html", method = RequestMethod.GET)
    public String selectArea(@ModelAttribute("sheet") final Sheet sheet,
			final ModelMap model) {
		model.addAttribute("columns", sheet.getColumns());
		model.addAttribute("rows", sheet.getRows());
        return "/iplug-pages/selectArea";
	}

    @RequestMapping(value = "/iplug-pages/selectArea.html", method = RequestMethod.POST)
    public String subitSelectArea(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int fromCol,
            @RequestParam final int toCol, @RequestParam final int fromRow, @RequestParam final int toRow) {
        SheetFilter.select(sheet, fromCol, toCol, fromRow, toRow);

        return "redirect:/iplug-pages/mapping.html";
	}

}