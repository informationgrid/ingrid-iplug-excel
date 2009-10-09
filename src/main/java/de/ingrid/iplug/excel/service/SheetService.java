package de.ingrid.iplug.excel.service;

import java.util.Iterator;
import java.util.List;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

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
	
	public static void removeColumn(Sheet sheet, int colIndex){
		List<Column> columns = sheet.getColumns();
		Iterator<Column> columnsIterator = columns.iterator();
		
		// handle Column
		int i = 0;
		while (columnsIterator.hasNext()) {
			columnsIterator.next();
			if(i == colIndex){
				columnsIterator.remove();
				break;
			}
			i++;
		}
		
		// handle the values
		Values values = sheet.getValues();
		Iterator<Point> pointIterator = values.getPointIterator();
		
		while (pointIterator.hasNext()) {
			Point point = (Point) pointIterator.next();
			if (point.getX() == 0) {
				pointIterator.remove();
			}
		}
	}
	
	public static void removeRow(Sheet sheet, int rowIndex){
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();

		// handle row
		int i = 0;
		while (rowIterator.hasNext()) {
			rowIterator.next();
			if(i == rowIndex){
				rowIterator.remove();
				break;
			}
			i++;
		}

		// handle the values
		Values values = sheet.getValues();
		Iterator<Point> pointIterator = values.getPointIterator();
		
		while (pointIterator.hasNext()) {
			Point point = (Point) pointIterator.next();
			if (point.getY() == rowIndex) {
				pointIterator.remove();
			}
		}
		
	}

}
