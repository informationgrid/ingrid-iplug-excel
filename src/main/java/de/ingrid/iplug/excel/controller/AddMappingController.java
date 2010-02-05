package de.ingrid.iplug.excel.controller;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.command.PlugdescriptionCommandObject;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetFilter;
import de.ingrid.iplug.excel.service.SheetsService;

@Controller
@SessionAttributes( { "plugDescription", "sheets" })
public class AddMappingController {

    @RequestMapping(value = "/iplug-pages/addMapping.html", method = RequestMethod.GET)
    public String editSheet(@ModelAttribute("plugDescription") final PlugdescriptionCommandObject plugDescription,
            @RequestParam final Integer sheetIndex, final Model model) throws IOException {
        final Sheets sheets = SheetsService.loadSheets(plugDescription, sheetIndex);
        // trim sheets
        final Iterator<Sheet> it = sheets.iterator();
        while (it.hasNext()) {
            // if trim return true, remove sheet
            if (SheetFilter.trimSheet(it.next())) {
                it.remove();
            }
        }
		model.addAttribute("sheets", sheets);
        return "redirect:/iplug-pages/previewExcelFile.html";
	}

}
