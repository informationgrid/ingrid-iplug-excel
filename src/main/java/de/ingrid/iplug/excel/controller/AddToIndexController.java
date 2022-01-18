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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.mapping.FieldType;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Sheet;

/**
 * Controller to add index fields.
 *
 */
@Controller
@SessionAttributes("sheet")
public class AddToIndexController {

    /**
     * Add index field
     *       
     * @param sheet
     * @param index
     * @param modelMap
     * @return
     * 		Web request "/iplug-pages/addToIndex"
     */
    @RequestMapping(value = "/iplug-pages/addToIndex.html", method = RequestMethod.GET)
    public String addToIndex(@ModelAttribute("sheet") final Sheet sheet,
            @RequestParam(required = true) final int index, final ModelMap modelMap) {
        modelMap.addAttribute("index", index);
        modelMap.addAttribute("type", sheet.getDocumentType());
        modelMap.addAttribute("fieldTypes", FieldType.values());

        AbstractEntry entry = null;
        switch (sheet.getDocumentType()) {
        case COLUMN:
            entry = sheet.getRow(index);
            break;
        case ROW:
            entry = sheet.getColumn(index);
            break;
        default:
            break;
        }
        modelMap.addAttribute("label", entry.getLabel());

        return "/iplug-pages/addToIndex";
	}

    /**
     * Submit added index field. 
     * 
     * @param sheet
     * @param index
     * @param fieldName
     * @param ownFieldName
     * @param fieldType
     * @param rank
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/addToIndex.html", method = RequestMethod.POST)
    public String addToIndexPost(@ModelAttribute("sheet") final Sheet sheet,
			@RequestParam(required = true) final int index,
			@RequestParam(required = false) final String fieldName,
			@RequestParam(required = false) final String ownFieldName,
            @RequestParam(required = true) final FieldType fieldType,
			@RequestParam(required = true) final float rank) {
		final DocumentType documentType = sheet.getDocumentType();
		AbstractEntry entry = null;
		switch (documentType) {
		case ROW:
            entry = sheet.getColumn(index);
			break;
		case COLUMN:
            entry = sheet.getRow(index);
			break;
		default:
			break;
		}
        final String label = !"".equals(ownFieldName) ? ownFieldName : !"".equals(fieldName) ? fieldName : entry
                .getLabel();
		entry.setLabel(label);
		entry.setMapped(true);
		entry.setRank(rank);
        entry.setFieldType(fieldType);

        return "redirect:/iplug-pages/mapping.html";
	}

    /**
     * Remove a index field.
     * 
     * @param sheet
     * @param index
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/removeFromIndex.html", method = RequestMethod.GET)
    public String removeFromIndex(@ModelAttribute("sheet") final Sheet sheet,
			@RequestParam(required = true) final int index) {
		final DocumentType documentType = sheet.getDocumentType();
		AbstractEntry entry = null;
		String label = null;
		switch (documentType) {
		case ROW:
            entry = sheet.getColumn(index);
            label = Column.getDefaultLabel(index);
			break;
		case COLUMN:
            entry = sheet.getRow(index);
            label = entry.getIndex() + "";
			break;
		default:
			break;
		}
		entry.getFilters().clear();
		entry.setMapped(false);
		entry.setRank(0);
		entry.setFieldType(FieldType.TEXT);
		entry.setLabel(label);

        return "redirect:/iplug-pages/mapping.html";
	}
}
