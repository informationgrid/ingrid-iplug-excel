package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class ExcludeDocumentController {

	private SheetService _sheetService;

	@Autowired
	public ExcludeDocumentController(SheetService sheetService) {
		_sheetService = sheetService;
	}

	@RequestMapping(value = "/iplug/excludeDocument.html", method = RequestMethod.GET)
	public String selectArea(@ModelAttribute("sheets") Sheets sheets) {
		return "/iplug/excludeDocument";
	}

	@RequestMapping(value = "/iplug/excludeDocument.html", method = RequestMethod.POST)
	public String subitSelectArea(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int index) {
		Sheet sheet = sheets.getSheets().get(0);
		DocumentType documentType = sheet.getDocumentType();
		if (documentType.equals(DocumentType.COLUMN)) {
			List<Column> columns = sheet.getColumns();
			Iterator<Column> iterator = columns.iterator();
			while (iterator.hasNext()) {
				Column column = (Column) iterator.next();
				if (index == column.getIndex()) {
					column.setExcluded(true);
					break;
				}
			}
		} else if (documentType.equals(DocumentType.ROW)) {
			List<Row> rows = sheet.getRows();
			Iterator<Row> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Row row = (Row) iterator.next();
				if (index == row.getIndex()) {
					row.setExcluded(true);
					break;
				}
			}
		}

		return "redirect:/iplug/mapping.html";
	}

}