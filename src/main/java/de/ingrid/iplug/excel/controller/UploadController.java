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
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import de.ingrid.iplug.excel.UploadBean;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetFilter;
import de.ingrid.iplug.excel.service.SheetsService;

/**
 * Controller for upload excel sheets.
 *
 */
@Controller
@RequestMapping("/iplug-pages/upload.html")
@SessionAttributes("sheets")
public class UploadController {

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @ModelAttribute("uploadBean")
    public UploadBean injectUploadBean() {
        return new UploadBean();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String firstRow() {
        return "/iplug-pages/upload";
	}

	/**
	 * 	Upload excel file.
	 * 
	 * @param uploadBean
	 * @param model
	 * @return
	 * 		Web request "redirect:/iplug-pages/previewExcelFile.html"
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String upload(@ModelAttribute("uploadBean") final UploadBean uploadBean,
			final Model model) throws IOException {
        // create sheets
        final Sheets sheets = SheetsService.createSheets(uploadBean.getFile());
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
