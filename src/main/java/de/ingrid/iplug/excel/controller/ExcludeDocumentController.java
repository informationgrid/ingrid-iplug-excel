/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2016 wemove digital solutions GmbH
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

import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheet;

/**
 * Controller to manage exclude documents.
 *
 */
@Controller
@SessionAttributes("sheet")
public class ExcludeDocumentController {

    @RequestMapping(value = "/iplug-pages/excludeDocument.html", method = RequestMethod.GET)
    public String excludeDocument(@ModelAttribute("sheet") final Sheet sheet) {
        return "/iplug-pages/excludeDocument";
	}

    /**
     * Submit selected exclusion.
     * 
     * @param sheet
     * @param index
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/excludeDocument.html", method = RequestMethod.POST)
    public String submitExcludeDocument(@ModelAttribute("sheet") final Sheet sheet,
			@RequestParam(required = true) final int index) {
        handleExclusion(sheet, index, true);
        return "redirect:/iplug-pages/mapping.html";
	}

    /**
     * Remove selected exclusion.
     * 
     * @param sheet
     * @param index
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/removeExclusion.html", method = RequestMethod.GET)
    public String removeExclusion(@ModelAttribute("sheet") final Sheet sheet,
			@RequestParam(required = true) final int index){
        handleExclusion(sheet, index, false);
        return "redirect:/iplug-pages/mapping.html";
	}

    /**
     * Handle exclusion. 
     * 
     * @param sheet
     * @param index
     * @param exclude
     */
    private void handleExclusion(final Sheet sheet, final int index, final boolean exclude) {
		final DocumentType documentType = sheet.getDocumentType();
		if (documentType.equals(DocumentType.COLUMN)) {
            sheet.getColumn(index).setExcluded(exclude);
		} else if (documentType.equals(DocumentType.ROW)) {
            sheet.getRow(index).setExcluded(exclude);
		}
	}
}
