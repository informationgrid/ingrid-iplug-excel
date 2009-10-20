package de.ingrid.iplug.excel.service;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Values;

public class SheetsServiceTest extends TestCase {

	public void testCreateSheets() throws Exception {
		SheetsService sheetsService = new SheetsService();
		Sheets sheets = sheetsService.createSheets(new File(
				"src/test/resources/mapping/test.xls"));
		List<Sheet> sheetList = sheets.getSheets();
		assertEquals(3, sheetList.size());
		Sheet sheet = sheetList.get(0);

		List<Row> rows = sheet.getRows();
		assertEquals(14, rows.size());

		List<Column> columns = sheet.getColumns();
		assertEquals(8, columns.size());

		Values values = sheet.getValues();
		assertEquals(112, values.getSize());

		assertEquals("Vorname", values.getValue(0, 0));
		assertEquals("Name", values.getValue(1, 0));
		assertEquals("Alter", values.getValue(2, 0));

		assertEquals("Foo", values.getValue(0, 1));
		assertEquals("Bar", values.getValue(1, 1));
		assertEquals(23.0, values.getValue(2, 1));

		assertEquals("Bar", values.getValue(0, 2));
		assertEquals("Foo", values.getValue(1, 2));
		assertEquals(32.0, values.getValue(2, 2));

		assertEquals("Foobar", values.getValue(0, 3));
		assertEquals("Foobar", values.getValue(1, 3));
		assertEquals(2323.0, values.getValue(2, 3));

	}
}
