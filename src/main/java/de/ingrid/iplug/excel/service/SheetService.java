package de.ingrid.iplug.excel.service;

import java.util.List;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

public class SheetService {
	
	public static Column getColumnByIndex(Sheet sheet, int index){
		List<Column> columns = sheet.getColumns();
		for (Column column : columns) {
			if(column.getIndex() == index){
				return column;
			}
		}
		return null;
	}
	
	public static Row getRowByIndex(Sheet sheet, int index){
		List<Row> rows = sheet.getRows();
		for (Row row : rows) {
			if(row.getIndex() == index){
				return row;
			}
		}
		return null;
	}

}
