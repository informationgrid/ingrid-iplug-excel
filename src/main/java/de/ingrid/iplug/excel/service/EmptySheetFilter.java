package de.ingrid.iplug.excel.service;

import java.io.Serializable;
import java.util.BitSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

@Service
public class EmptySheetFilter extends ExcludeFilter {

	@Autowired
	public EmptySheetFilter(SheetService sheetService) {
		super(sheetService);
	}

	public BitSet filterColumns(Sheet sheet) {
		BitSet bitSet = new BitSet(sheet.getColumns().size());
		List<Column> columns = sheet.getColumns();
		List<Row> rows = sheet.getRows();
		Values values = sheet.getValues();

		for (Column column : columns) {
			boolean empty = true;
			for (Row row : rows) {
				Serializable value = values.getValue(column.getIndex(), row
						.getIndex());
				if (value != null && !value.toString().equals("")) {
					empty = false;
				}
			}
			bitSet.set(column.getIndex(), empty);
		}

		return bitSet;
	}

	public BitSet filterRows(Sheet sheet) {
		BitSet bitSet = new BitSet(sheet.getRows().size());
		List<Column> columns = sheet.getColumns();
		List<Row> rows = sheet.getRows();
		Values values = sheet.getValues();

		for (Row row : rows) {
			boolean empty = true;
			for (Column column : columns) {
				Serializable value = values.getValue(column.getIndex(), row
						.getIndex());
				if (value != null && !value.toString().equals("")) {
					empty = false;
				}
			}
			bitSet.set(row.getIndex(), empty);
		}
		return bitSet;
	}

}
