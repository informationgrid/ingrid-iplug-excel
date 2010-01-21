package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;

@Controller
@SessionAttributes("sheets")
public class ExcludeDocumentController {

	public ExcludeDocumentController() {
		//
	}

    @RequestMapping(value = "/iplug-pages/excludeDocument.html", method = RequestMethod.GET)
	public String excludeDocument(@ModelAttribute("sheets") final Sheets sheets) {
        return "/iplug-pages/excludeDocument";
	}

    @RequestMapping(value = "/iplug-pages/excludeDocument.html", method = RequestMethod.POST)
	public String submitExcludeDocument(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index) {
		handleExclusion(sheets, index, true);
        return "redirect:/iplug-pages/mapping.html";
	}

    @RequestMapping(value = "/iplug-pages/removeExclusion.html", method = RequestMethod.GET)
	public String removeExclusion(@ModelAttribute("sheets") final Sheets sheets,
			@RequestParam(required = true) final int index){
		handleExclusion(sheets, index, false);
        return "redirect:/iplug-pages/mapping.html";
	}

	private void handleExclusion(final Sheets sheets, final int index, final boolean exclude) {
		final Sheet sheet = sheets.getSheets().get(0);
		final DocumentType documentType = sheet.getDocumentType();
		if (documentType.equals(DocumentType.COLUMN)) {
			final List<Column> columns = sheet.getColumns();
			final Iterator<Column> iterator = columns.iterator();
			while (iterator.hasNext()) {
				final Column column = iterator.next();
				if (index == column.getIndex()) {
					column.setExcluded(exclude);
					break;
				}
			}
		} else if (documentType.equals(DocumentType.ROW)) {
			final List<Row> rows = sheet.getRows();
			final Iterator<Row> iterator = rows.iterator();
			while (iterator.hasNext()) {
				final Row row = iterator.next();
				if (index == row.getIndex()) {
					row.setExcluded(exclude);
					break;
				}
			}
		}
	}


}