package de.ingrid.iplug.excel.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.service.SheetsService;

/**
 * Controller to load a sheet of an excel sheet.
 *
 */
@Controller
@SessionAttributes( { "plugDescription", "sheet" })
public class EditMappingController {

    /**
     * Load excel sheet with sheet index. 
     * 
     * @param plugDescription
     * @param sheetIndex
     * @param model
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     * @throws IOException
     */
    @RequestMapping(value = "/iplug-pages/editMapping.html", method = RequestMethod.GET)
    public String editSheet(@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugDescription,
            @RequestParam final Integer sheetIndex, final Model model) throws IOException {
        final Sheet sheet = SheetsService.loadSheet(plugDescription, sheetIndex);
        model.addAttribute("sheet", sheet);
        return "redirect:/iplug-pages/mapping.html";
	}
}
