/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2020 wemove digital solutions GmbH
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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.mapping.Filter;
import de.ingrid.admin.mapping.Filter.FilterType;
import de.ingrid.iplug.excel.model.AbstractEntry;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.service.SheetFilter;

/**
 * Controller to manage filter functionality
 *
 */
@Controller
@SessionAttributes("sheet")
public class AddFilterController {

    /**
     * Add filter to index.
     * 
     * @param sheet
     * @param index
     * @param modelMap
     * @return
     * 		Web request "redirect:/iplug-pages/addFilter"
     */
    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.GET)
    public String addFilter(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            final ModelMap modelMap) {
        final DocumentType documentType = sheet.getDocumentType();
        modelMap.addAttribute("type", documentType);
        modelMap.addAttribute("index", index);

		AbstractEntry doc = null;
		switch (documentType) {
		case ROW:
            doc = sheet.getColumn(index);
			break;
		case COLUMN:
            doc = sheet.getRow(index);
			break;
		default:
			break;
		}
        modelMap.addAttribute("label", doc.getLabel());

        final List<FilterType> filterTypes = new ArrayList<FilterType>();
		final FieldType fieldType = doc.getFieldType();
		switch (fieldType) {
		case BOOLEAN:
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			break;
		case DATE:
            filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.BEFORE);
			filterTypes.add(FilterType.AFTER);
			break;
		case KEYWORD:
		case TEXT:
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			filterTypes.add(FilterType.CONTAINS);
			filterTypes.add(FilterType.NOT_CONTAINS);
			break;
		case NUMBER:
			filterTypes.add(FilterType.GREATER_THAN);
			filterTypes.add(FilterType.LOWER_THAN);
			filterTypes.add(FilterType.EQUAL);
			filterTypes.add(FilterType.NOT_EQUAL);
			break;
		default:
			break;
		}

        modelMap.addAttribute("filterTypes", filterTypes);
        return "/iplug-pages/addFilter";
	}

    /**
     * Submit a added filter to index.
     * 
     * @param sheet
     * @param index
     * @param filterType
     * @param expression
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/addFilter.html", method = RequestMethod.POST)
    public String addFilterPost(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            @RequestParam final FilterType filterType, @RequestParam final String expression) {
		Filter filter = null;
        if (filterType == FilterType.GREATER_THAN || filterType == FilterType.LOWER_THAN) {
            filter = new Filter(Double.parseDouble(expression), filterType);
        } else {
            filter = new Filter(expression, filterType);
		}

        switch (sheet.getDocumentType()) {
		case ROW:
            final Column col = sheet.getColumn(index);
			col.addFilter(filter);
            SheetFilter.filter(sheet, col, filter);
			break;
		case COLUMN:
            final Row row = sheet.getRow(index);
			row.addFilter(filter);
            SheetFilter.filter(sheet, row, filter);
			break;
		default:
			break;
		}

        return "redirect:/iplug-pages/mapping.html";
	}

    /**
     * Remove filter from index.
     * 
     * @param sheet
     * @param index
     * @param filterIndex
     * @return
     * 		Web request "redirect:/iplug-pages/mapping.html"
     */
    @RequestMapping(value = "/iplug-pages/removeFilter.html", method = RequestMethod.GET)
    public String removeFilter(@ModelAttribute("sheet") final Sheet sheet, @RequestParam final int index,
            @RequestParam final int filterIndex) {
		final DocumentType documentType = sheet.getDocumentType();
		switch (documentType) {
		case ROW:
            final Column col = sheet.getColumn(index);
			col.removeFilter(filterIndex);
			break;
		case COLUMN:
            final Row row = sheet.getRow(index);
			row.removeFilter(filterIndex);
			break;
		default:
			break;
		}
        SheetFilter.filter(sheet);

        return "redirect:/iplug-pages/mapping.html";
	}
}
