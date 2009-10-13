package de.ingrid.iplug.excel.service;

import java.util.BitSet;

import de.ingrid.iplug.excel.model.Sheet;

public class EmptySheetFilterTest extends AbstractSheetTest {

	public void testFilter() throws Exception {
		ISheetFilter filter = new EmptySheetFilter();
		Sheet sheet = getTestSheet();

		// now test it
		BitSet bitSet = filter.filterRows(sheet);
		assertEquals(3, bitSet.length());
		assertTrue(bitSet.size() > 3);
		assertFalse(bitSet.get(0));
		assertFalse(bitSet.get(1));
		assertTrue(bitSet.get(2));

		bitSet = filter.filterColumns(sheet);
		assertEquals(3, bitSet.length());
		assertTrue(bitSet.size() > 3);
		assertFalse(bitSet.get(0));
		assertFalse(bitSet.get(1));
		assertTrue(bitSet.get(2));

	}

}
