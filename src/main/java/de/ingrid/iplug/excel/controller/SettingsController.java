package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class SettingsController {

	private final SheetService _sheetService;

	@Autowired
	public SettingsController(SheetService sheetService) {
		_sheetService = sheetService;
	}

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.GET)
	public String settings(ModelMap model,
			@ModelAttribute("sheets") Sheets sheets) {

		model.addAttribute("documentTypes", DocumentType.values());
		return "/iplug/settings";
	}

	@RequestMapping(value = "/iplug/settings.html", method = RequestMethod.POST)
	public String postSettings(@ModelAttribute("sheets") Sheets sheets) {

		Sheet sheet = sheets.getSheets().get(0);
		List<Column> columns = sheet.getColumns();
		List<Row> rows = sheet.getRows();
		Map<Integer, List<Comparable<?>>> valuesAsMap = sheet.getValuesAsMap();
		Iterator<Integer> valuesAsMapIterator = valuesAsMap.keySet().iterator();

		// handle firstIsLabel
		if (sheet.isFirstIsLabel()) {
			// we map columns to index fields, a row is a doc
			if (sheet.getDocumentType().equals(DocumentType.ROW)) {
				List<Comparable<?>> firstRowValues = valuesAsMap.get(0);

				// set the label
				for (int i = 0; i < columns.size(); i++) {
					Column column = columns.get(i);
					Comparable<?> value = firstRowValues.get(i);
					column.setLabel(value.toString());
				}

				_sheetService.excludeRow(sheet, 0);
			} else if (sheet.getDocumentType().equals(DocumentType.COLUMN)) {
				// we map rows to index fields, a column is a doc

				// set the label
				for (int i = 0; i < rows.size(); i++) {
					Row row = rows.get(i);
					int rowIndex = row.getIndex();

					while (valuesAsMapIterator.hasNext()) {
						valuesAsMapIterator.next();
						Comparable<?> firstColValue = valuesAsMap.get(rowIndex)
								.get(0);
						row.setLabel(firstColValue + "");
					}
				}

				_sheetService.excludeColumn(sheet, 0);
			}
		}

		return "redirect:/iplug/mapping.html";
	}

}
