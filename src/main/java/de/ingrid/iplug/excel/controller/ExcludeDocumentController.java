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

	@RequestMapping(value = "/iplug/excludeDocument.html", method = RequestMethod.GET)
	public String excludeDocument(@ModelAttribute("sheets") Sheets sheets) {
		return "/iplug/excludeDocument";
	}

	@RequestMapping(value = "/iplug/excludeDocument.html", method = RequestMethod.POST)
	public String submitExcludeDocument(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int index) {
		handleExclusion(sheets, index, true);
		return "redirect:/iplug/mapping.html";
	}
	
	@RequestMapping(value = "/iplug/removeExclusion.html", method = RequestMethod.GET)
	public String removeExclusion(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int index){
		handleExclusion(sheets, index, false);
		return "redirect:/iplug/mapping.html";
	}

	private void handleExclusion(Sheets sheets, final int index, boolean exclude) {
		Sheet sheet = sheets.getSheets().get(0);
		DocumentType documentType = sheet.getDocumentType();
		if (documentType.equals(DocumentType.COLUMN)) {
			List<Column> columns = sheet.getColumns();
			Iterator<Column> iterator = columns.iterator();
			while (iterator.hasNext()) {
				Column column = (Column) iterator.next();
				if (index == column.getIndex()) {
					column.setExcluded(exclude);
					break;
				}
			}
		} else if (documentType.equals(DocumentType.ROW)) {
			List<Row> rows = sheet.getRows();
			Iterator<Row> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Row row = (Row) iterator.next();
				if (index == row.getIndex()) {
					row.setExcluded(exclude);
					break;
				}
			}
		}
	}
	

}