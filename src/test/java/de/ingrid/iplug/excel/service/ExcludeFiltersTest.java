package de.ingrid.iplug.excel.service;

import java.util.List;

import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

public class ExcludeFiltersTest extends AbstractSheetTest {

	public void testFilter() throws Exception {
		ExcludeFilters excludeFilters = new ExcludeFilters(
				new EmptySheetFilter(new SheetService()));
		Sheet sheet = getTestSheet();
		List<Row> rows = sheet.getRows();
		assertFalse(rows.get(0).isExcluded());
		assertFalse(rows.get(1).isExcluded());
		assertFalse(rows.get(2).isExcluded());

		excludeFilters.filter(sheet);
		rows = sheet.getRows();
		assertEquals(3, rows.size());
		assertFalse(rows.get(0).isExcluded());
		assertFalse(rows.get(1).isExcluded());
		assertTrue(rows.get(2).isExcluded());
	}
}
