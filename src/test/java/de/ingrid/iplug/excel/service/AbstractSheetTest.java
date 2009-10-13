package de.ingrid.iplug.excel.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

public abstract class AbstractSheetTest extends TestCase {

	protected List<Column> getTestColumns() {
		List<Column> columns = new ArrayList<Column>();
		Column column1 = new Column(0);
		column1.setLabel("one");

		Column column2 = new Column(1);
		column2.setLabel("two");

		Column column3 = new Column(2);
		column3.setLabel("");

		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		return columns;
	}

	protected List<Row> getTestRows() {
		List<Row> rows = new ArrayList<Row>();
		Row row1 = new Row(0);
		row1.setLabel("1");
		Row row2 = new Row(1);
		row2.setLabel("2");
		Row row3 = new Row(2);
		row3.setLabel("");
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);
		return rows;
	}

	protected Sheet getTestSheet() {
		Sheet sheet = new Sheet();
		List<Row> rows = getTestRows();
		for (Row row : rows) {
			sheet.addRow(row);
		}
		List<Column> columns = getTestColumns();
		for (Column column : columns) {
			sheet.addColumn(column);
		}
		Values values = new Values();
		for (Row row : rows) {
			for (Column column : columns) {
				String value = row.getIndex() == 3 ? "" : row.getLabel();
				value = value.equals("") ? "" : column.getIndex() == 3 ? ""
						: column.getLabel();
				values.addValue(new Point(column.getIndex(), row.getIndex()),
						value);
			}
		}
		sheet.setValues(values);
		return sheet;
	}
}
