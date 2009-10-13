package de.ingrid.iplug.excel.service;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Point;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheet;

@Service
public class SelectAreaExcludeFilter extends ExcludeFilter {

	private int _fromCol;
	private int _toCol;
	private int _fromRow;
	private int _toRow;

	public void excludeNonSelectedArea(Sheet sheet, int fromCol, int toCol,
			int fromRow, int toRow) {
		_fromCol = fromCol;
		_toCol = toCol;
		_fromRow = fromRow;
		_toRow = toRow;
		BitSet columnBitSet = filterColumns(sheet);
		excludeColumns(sheet, columnBitSet);
		BitSet rowBitSet = filterRows(sheet);
		excludeRows(sheet, rowBitSet);

		int fromX = columnBitSet.nextClearBit(0);
		int fromY = rowBitSet.nextClearBit(0);
		int toX = columnBitSet.nextSetBit(fromX);
		int toY = rowBitSet.nextSetBit(fromY) - 1;

		sheet.setSelectFrom(new Point(fromX, fromY));
		sheet.setSelectTo(new Point(toX, toY));

	}

	public BitSet filterColumns(Sheet sheet) {
		BitSet bitSet = new BitSet();
		List<Column> columns = sheet.getColumns();
		Iterator<Column> columnIterator = columns.iterator();
		while (columnIterator.hasNext()) {
			Column column = (Column) columnIterator.next();
			int colIndex = column.getIndex();
			if (colIndex < _fromCol || colIndex > _toCol) {
				bitSet.set(colIndex);
			}
		}
		return bitSet;
	}

	public BitSet filterRows(Sheet sheet) {
		BitSet bitSet = new BitSet();
		List<Row> rows = sheet.getRows();
		Iterator<Row> rowIterator = rows.iterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			int rowIndex = row.getIndex();
			if (rowIndex < _fromRow || rowIndex > _toRow) {
				bitSet.set(rowIndex);
			}
		}
		return bitSet;
	}

}
