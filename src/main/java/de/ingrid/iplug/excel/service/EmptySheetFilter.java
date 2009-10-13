package de.ingrid.iplug.excel.service;

import java.io.Serializable;
import java.util.BitSet;
import java.util.List;

import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;
import de.ingrid.iplug.excel.model.Values;

@Service
public class EmptySheetFilter extends ExcludeFilter {

	public void excludeEmtpyRowsAndColumns(Sheet sheet) {
		BitSet columnBitSet = filterColumns(sheet);
		excludeColumns(sheet, columnBitSet);
		BitSet rowBitSet = filterRows(sheet);
		excludeRows(sheet, rowBitSet);

		int fromX = columnBitSet.nextClearBit(0) >-1 ? columnBitSet.nextClearBit(0) : 0;
		int fromY = rowBitSet.nextClearBit(0) > -1 ? rowBitSet.nextClearBit(0) : 0;
		int toX = columnBitSet.nextSetBit(fromX) > -1 ? (columnBitSet.nextSetBit(fromX)-1) : (sheet.getColumns().size() -1);
		int toY = rowBitSet.nextSetBit(fromY) > - 1 ? (rowBitSet.nextSetBit(fromY) -1) : (sheet.getRows().size() -1) ;
		
		System.out.println("EmptySheetFilter.excludeEmtpyRowsAndColumns() from " +fromX +" " +fromY +" to " +toX +" " +toY);

		sheet.setSelectFrom(new Point(fromX, fromY));
		sheet.setSelectTo(new Point(toX, toY));
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
