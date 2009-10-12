package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class SelectAreaController {

	private final SheetService _sheetService;

	@Autowired
	public SelectAreaController(SheetService sheetService) {
		_sheetService = sheetService;
	}

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.GET)
	public String selectArea(@ModelAttribute("sheets") Sheets sheets,
			ModelMap model) {
		Sheet sheet = sheets.getSheets().get(0);
		model.addAttribute("columns", sheet.getColumns());
		model.addAttribute("rows", sheet.getRows());
		return "/iplug/selectArea";
	}

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.POST)
	public String subitSelectArea(@ModelAttribute("sheets") Sheets sheets,
			@RequestParam(required = true) final int fromCol,
			@RequestParam(required = true) final int toCol,
			@RequestParam(required = true) final int fromRow,
			@RequestParam(required = true) final int toRow) {

		// exclude columns
		Sheet sheet = sheets.getSheets().get(0);
		List<Column> columns = sheet.getColumns();
		Iterator<Column> columnIterator = columns.iterator();
		while (columnIterator.hasNext()) {
			Column column = (Column) columnIterator.next();
			int colIndex = column.getIndex();
			if (colIndex < fromCol || colIndex > toCol) {
				column.setExcluded(true);
			}else{
				column.setExcluded(false);
			}
		}

		// exclude rows
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			int rowIndex = row.getIndex();
			if (rowIndex < fromRow || rowIndex > toRow) {
				row.setExcluded(true);
			}else{
				row.setExcluded(false);
			}
		}

		Point pointFrom = new Point();
		pointFrom.setX(fromCol);
		pointFrom.setY(fromRow);
		sheet.setSelectFrom(pointFrom);

		Point pointTo = new Point();
		pointTo.setX(toCol);
		pointTo.setY(toRow);
		sheet.setSelectTo(pointTo);

		return "redirect:/iplug/mapping.html";
	}

}