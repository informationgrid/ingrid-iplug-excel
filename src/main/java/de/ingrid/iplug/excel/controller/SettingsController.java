/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2021 wemove digital solutions GmbH
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheet;

/**
 * Controller to define Settings for mapped excel sheet.
 *
 */
@Controller
@SessionAttributes("sheet")
public class SettingsController {

    /**
     * Add DocumentType into ModelMap. 
     * 
     * @param model
     * @param sheet
     * @return
     * 		Web request "/iplug-pages/settings"
     */
    @RequestMapping(value = "/iplug-pages/settings.html", method = RequestMethod.GET)
    public String settings(final ModelMap model, @ModelAttribute("sheet") final Sheet sheet) {
		model.addAttribute("documentTypes", DocumentType.values());
        return "/iplug-pages/settings";
	}

    /**
     * Submit settings.
     * 
     * @param sheet
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/settings.html", method = RequestMethod.POST)
    public String postSettings(@ModelAttribute("sheet") final Sheet sheet) {
        return "redirect:/iplug-pages/mapping.html";
	}
}
