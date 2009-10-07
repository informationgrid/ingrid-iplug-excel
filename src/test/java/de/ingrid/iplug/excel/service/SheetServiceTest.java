package de.ingrid.iplug.excel.service;

import java.io.Serializable;
import java.util.List;

import junit.framework.TestCase;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Matrix;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;

public class SheetServiceTest extends TestCase {

	public void testGetAllRowEntries() throws Exception {
		SheetService sheetService = new SheetService();
		Row row = new Row();
		row.setIndex(2);
		Matrix matrix = new Matrix();
		matrix.addValue(new Point(1, 3), "3");
		matrix.addValue(new Point(1, 87), "87");
		matrix.addValue(new Point(1, 23), "23");
		matrix.addValue(new Point(2, 2), "2");
		matrix.addValue(new Point(22, 2), "22");
		matrix.addValue(new Point(44, 2), "44");
		matrix.addValue(new Point(45, 2), "45");

		List<Serializable> allRowEntries = sheetService.getAllValues(row,
				matrix);
		assertEquals(4, allRowEntries.size());
		assertTrue(allRowEntries.contains("2"));
		assertTrue(allRowEntries.contains("22"));
		assertTrue(allRowEntries.contains("44"));
		assertTrue(allRowEntries.contains("45"));
	}

	public void testGetAllColumnEntries() throws Exception {
		SheetService sheetService = new SheetService();
		Column column = new Column();
		column.setIndex(1);
		Matrix matrix = new Matrix();
		matrix.addValue(new Point(1, 3), "3");
		matrix.addValue(new Point(1, 87), "87");
		matrix.addValue(new Point(1, 23), "23");
		matrix.addValue(new Point(2, 2), "2");
		matrix.addValue(new Point(22, 2), "22");
		matrix.addValue(new Point(44, 2), "44");
		matrix.addValue(new Point(45, 2), "45");

		List<Serializable> allRowEntries = sheetService.getAllValues(column,
				matrix);
		assertEquals(3, allRowEntries.size());
		assertTrue(allRowEntries.contains("3"));
		assertTrue(allRowEntries.contains("87"));
		assertTrue(allRowEntries.contains("23"));
	}
}
