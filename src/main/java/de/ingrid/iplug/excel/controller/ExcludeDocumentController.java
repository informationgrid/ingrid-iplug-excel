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