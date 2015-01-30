/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2015 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * **************************************************#
 */
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
