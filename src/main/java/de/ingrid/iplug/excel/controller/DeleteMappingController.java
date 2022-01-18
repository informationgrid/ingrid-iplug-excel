/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2022 wemove digital solutions GmbH
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
