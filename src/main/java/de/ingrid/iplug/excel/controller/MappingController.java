/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2018 wemove digital solutions GmbH
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Sheet;

/**
 * Controller to display a mapping document.
 *
 */
@Controller
@SessionAttributes("sheet")
public class MappingController {

    public static final int HITS_PER_PAGE = 10;

    /**
     * Show a mapping document.
     * 
     * @param sheet
     * @param begin
     * @param modelMap
     * @return
     * 		Web request "redirect:/iplug-pages/mapping"
     */
    @RequestMapping(value = "/iplug-pages/mapping.html", method = RequestMethod.GET)
    public String mapping(@ModelAttribute("sheet") final Sheet sheet, @RequestParam(required = false) Integer begin,
            final ModelMap modelMap) {
		if (begin == null || begin < 0) {
            begin = 0;
		}

        final int last = sheet.getVisibleRows().size() - 1;
        final int end = Math.min(last, begin + HITS_PER_PAGE - 1);
        final int prev = Math.max(0, begin - HITS_PER_PAGE);
        final int nextBegin = Math.min(last, end + 1);
        final int nextEnd = Math.min(last, end + HITS_PER_PAGE);

        modelMap.addAttribute("begin", begin);
        modelMap.addAttribute("end", end);
        modelMap.addAttribute("prev", prev);
        modelMap.addAttribute("nextBegin", nextBegin);
        modelMap.addAttribute("nextEnd", nextEnd);
        modelMap.addAttribute("last", last);

        return "/iplug-pages/mapping";
	}

}
