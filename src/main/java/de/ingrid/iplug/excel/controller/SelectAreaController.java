package de.ingrid.iplug.excel.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class SelectAreaController {

	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.GET)
	public String selectArea(@ModelAttribute("sheets") Sheets sheets, ModelMap model) {
		Sheet sheet = sheets.getSheets().get(0);
		model.addAttribute("columns", sheet.getColumns());
		model.addAttribute("rows", sheet.getRows());
		return "/iplug/selectArea";
	}
	
	@RequestMapping(value = "/iplug/selectArea.html", method = RequestMethod.POST)
	public String subitSelectArea(@ModelAttribute("sheets") Sheets sheets, @RequestParam(required=true) final int fromCol,
			@RequestParam(required=true) final int toCol, @RequestParam(required=true) final int fromRow, 
			@RequestParam(required=true) final int toRow) {
		
		Sheet sheet = sheets.getSheets().get(0);
		List<Column> columns = sheet.getColumns();
		Iterator<Column> columnIterator = columns.iterator();
		while (columnIterator.hasNext()) {
			Column column = (Column) columnIterator.next();
			int colIndex = column.getIndex();
			if(colIndex < fromCol || colIndex > toCol){
			//	System.out.println("remove col " +column.getLabel());
				SheetService.removeColumn(sheet, colIndex);
			}
		}
		
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			int rowIndex = row.getIndex();
			if(rowIndex < fromCol || rowIndex > toCol){
				//System.out.println("remove row " +row.getLabel());
				SheetService.removeRow(sheet, rowIndex);
			}
			
		}
		
		return "redirect:/iplug/mapping.html";
	}

	

}