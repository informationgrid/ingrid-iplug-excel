package de.ingrid.iplug.excel.service;

import java.util.BitSet;
import java.util.List;

import de.ingrid.admin.mapping.Filter;
import de.ingrid.admin.mapping.Filter.FilterType;
import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Sheet;

public class AddToIndexFilterTest extends AbstractSheetTest {

	public void testAddToIndex() throws Exception {
		Sheet testSheet = getTestSheet();
		List<Column> columns = testSheet.getColumns();
		for (Column column : columns) {
			column.setMapped(true);
		}
		AddToIndexFilter filter = new AddToIndexFilter();

		BitSet bitSet = filter.filterRows(testSheet);
		assertFalse(bitSet.get(0));
		assertFalse(bitSet.get(1));
		assertFalse(bitSet.get(2));

		columns = testSheet.getColumns();
		Column column = columns.get(0);
		column.addFilter(new Filter("one1", FilterType.NOT_EQUAL));

		bitSet = filter.filterRows(testSheet);
		assertTrue(bitSet.get(0));
		assertFalse(bitSet.get(1));
		assertFalse(bitSet.get(2));
	}
}
