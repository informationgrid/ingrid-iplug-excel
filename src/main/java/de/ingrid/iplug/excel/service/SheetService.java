package de.ingrid.iplug.excel.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

@Service
public class SheetService {

	public Column getColumnByIndex(Sheet sheet, int index) {
		List<Column> columns = sheet.getColumns();
		for (Column column : columns) {
			if (column.getIndex() == index) {
				return column;
			}
		}
		return null;
	}

	public Row getRowByIndex(Sheet sheet, int index) {
		List<Row> rows = sheet.getRows();
		for (Row row : rows) {
			if (row.getIndex() == index) {
				return row;
			}
		}
		return null;
	}

	public void excludeColumn(Sheet sheet, int colIndex) {
		List<Column> columns = sheet.getColumns();
		Iterator<Column> columnsIterator = columns.iterator();

		// handle Column
		while (columnsIterator.hasNext()) {
			Column column = (Column) columnsIterator.next();
			if (column.getIndex() == colIndex) {
				column.setExcluded(true);
			}
		}

	}

	public void excludeRow(Sheet sheet, int rowIndex) {
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();

		// handle row
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getIndex() == rowIndex) {
				row.setExcluded(true);
			}
		}

	}

}
