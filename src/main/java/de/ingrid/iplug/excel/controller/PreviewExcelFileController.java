/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 wemove digital solutions GmbH
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheets;

/**
 * Controller to preview excel file.
 *
 */
@Controller
@SessionAttributes( { "sheets", "sheet" })
public class PreviewExcelFileController {

    /**
     * Call settings.
     * 
     * @param model
     * @param sheets
     * @return
     * 		If sheet exist return web request "redirect:/iplug-pages/settings.html" else "/iplug-pages/previewExcelFile"
     */
    @RequestMapping(value = "/iplug-pages/previewExcelFile.html", method = RequestMethod.GET)
    public String settings(final Model model, @ModelAttribute("sheets") final Sheets sheets) {
        if (sheets.getSheets().size() == 1) {
            model.addAttribute("sheet", sheets.getSheets().get(0));
            return "redirect:/iplug-pages/settings.html";
        }
        return "/iplug-pages/previewExcelFile";
	}

    /**
     * Submit selected sheet.
     * 
     * @param model
     * @param sheets
     * @param index
     * @return
     * 		If index exist return web request "redirect:/iplug-pages/settings.html" else "/iplug-pages/previewExcelFile"
     */
    @RequestMapping(value = "/iplug-pages/previewExcelFile.html", method = RequestMethod.POST)
    public String postSettings(final Model model, @ModelAttribute("sheets") final Sheets sheets,
            @RequestParam(value = "sheetIndex", required = false) final Integer index) {
        if (null == index) {
            // return to previous views
            return "/iplug-pages/previewExcelFile";
		}
        // select sheet and go on
        model.addAttribute("sheet", sheets.getSheet(index));
        return "redirect:/iplug-pages/settings.html";
	}

}
