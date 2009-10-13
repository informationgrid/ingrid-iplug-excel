package de.ingrid.iplug.excel.service;

import java.util.BitSet;
import java.util.List;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

public abstract class ExcludeFilter implements ISheetFilter {

	public void excludeRows(Sheet sheet, BitSet bitSet) {
		List<Row> rows = sheet.getRows();
		for (int i = 0; i < rows.size(); i++) {
			boolean exclude = bitSet.get(i);
			rows.get(i).setExcluded(exclude);
		}
	}

	public void excludeColumns(Sheet sheet, BitSet bitSet) {
		List<Column> columns = sheet.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			boolean exclude = bitSet.get(i);
			columns.get(i).setExcluded(exclude);
		}
	}
}
