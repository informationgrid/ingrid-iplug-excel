package de.ingrid.iplug.excel.service;

import java.util.List;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

public class SelectAreaExcludeFilterTest extends AbstractSheetTest {

	public void testSubselect() throws Exception {
		SelectAreaExcludeFilter filter = new SelectAreaExcludeFilter();
		Sheet testSheet = getTestSheet();
		filter.excludeNonSelectedArea(testSheet, 1, 2, 1, 2);
		List<Column> columns = testSheet.getColumns();
		assertEquals(3, columns.size());
		assertTrue(columns.get(0).isExcluded());
		assertFalse(columns.get(1).isExcluded());
		assertFalse(columns.get(2).isExcluded());

		List<Row> rows = testSheet.getRows();
		assertTrue(rows.get(0).isExcluded());
		assertFalse(rows.get(1).isExcluded());
		assertFalse(rows.get(2).isExcluded());

	}
}
