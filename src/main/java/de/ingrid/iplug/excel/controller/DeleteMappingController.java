package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

/**
 * Controller to delete mapping excel sheet.
 *
 */
@Controller
@SessionAttributes( { "plugDescription" })
public class DeleteMappingController {

    /**
     * Delete an existing mapped excel sheet. 
     * 
     * @param plugDescription
     * @param sheetIndex
     * @return
     * 		Web request "redirect:/iplug-pages/listMappings.html"
     */
    @RequestMapping(value = "/iplug-pages/deleteMapping.html", method = RequestMethod.POST)
    public String deleteMapping(@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugDescription,
            @RequestParam(value = "sheetIndex", required = true) final int sheetIndex) {
        final Sheets sheets = (Sheets) plugDescription.get("sheets");
        final Sheet sheet = sheets.getSheets().get(sheetIndex);
        sheets.removeSheet(sheet);
        return "redirect:/iplug-pages/listMappings.html";
	}
}
