package de.ingrid.iplug.excel.service;

import de.ingrid.iplug.excel.model.Sheet;

public class AddToIndexFilterTest extends AbstractSheetTest {

	public void testAddToIndex() throws Exception {
		Sheet testSheet = getTestSheet();
		AddToIndexFilter filter = new NumericFilter();
	}
}
