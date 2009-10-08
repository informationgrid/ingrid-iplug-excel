package de.ingrid.iplug.excel.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.DocumentType;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

@Controller
@SessionAttributes("sheets")
public class SettingsController {

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
		Iterator<Column> columnsIterator = columns.iterator();
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();
		Values values = sheet.getValues();
		Iterator<Point> pointIterator = values.getPointIterator();
		Map<Integer, List<Serializable>> valuesAsMap = sheet.getValuesAsMap();
		Iterator<Integer> valuesAsMapIterator = valuesAsMap.keySet().iterator();

		// handle firstIsLabel
		if (sheet.isFirstIsLabel()) {
			// we map columns to index fields, a row is a doc
			if (sheet.getDocumentType().equals(DocumentType.ROW)) {
				List<Serializable> firstRowValues = valuesAsMap.get(0);

				// set the label
				for (int i = 0; i < columns.size(); i++) {
					Column column = columns.get(i);
					column.setLabel(firstRowValues.get(i).toString());
				}

				// remove first row and its values
				while (rowIterator.hasNext()) {
					rowIterator.next();
					rowIterator.remove();
					break;
				}
				while (pointIterator.hasNext()) {
					Point point = (Point) pointIterator.next();
					if (point.getY() == 0) {
						pointIterator.remove();
					}
				}
			}else if(sheet.getDocumentType().equals(DocumentType.COLUMN)){
				// we map rows to index fields, a column is a doc
				
				// set the label
				for(int i=0; i<rows.size(); i++){
					Row row = rows.get(i);
					int rowIndex = row.getIndex();
					
					while (valuesAsMapIterator.hasNext()) {
						valuesAsMapIterator.next();
						Serializable firstColValue = valuesAsMap.get(rowIndex).get(0);
						row.setLabel(firstColValue+"");
					}
				}
				
				// remove first column and its values
				while (columnsIterator.hasNext()) {
					columnsIterator.next();
					columnsIterator.remove();
					break;
				}
				while (pointIterator.hasNext()) {
					Point point = (Point) pointIterator.next();
					if (point.getX() == 0) {
						pointIterator.remove();
					}
				}
				
			}
		}
		
		
		return "redirect:/iplug/mapping.html";
	}

}
