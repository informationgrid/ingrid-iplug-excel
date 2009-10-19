package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Sheet implements Externalizable {

	private String _fileName;
	private String _description;
	private int _sheetIndex;
	private List<Column> _columns = new ArrayList<Column>();
	private List<Row> _rows = new ArrayList<Row>();
	private DocumentType _documentType = DocumentType.ROW;
	private boolean _firstIsLabel;
	private Point _selectFrom = new Point();
	private Point _selectTo = new Point();
	private transient Values _values = new Values();
	private transient byte[] _workBook;

	public String getFileName() {
		return _fileName;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getSheetIndex() {
		return _sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		_sheetIndex = sheetIndex;
	}

	public List<Column> getColumns() {
		return _columns;
	}

	public void setColumns(List<Column> columns) {
		_columns = columns;
	}

	public List<Row> getRows() {
		return _rows;
	}

	public void setRows(List<Row> rows) {
		_rows = rows;
	}

	public DocumentType getDocumentType() {
		return _documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		_documentType = documentType;
	}

	public boolean isFirstIsLabel() {
		return _firstIsLabel;
	}

	public void setFirstIsLabel(boolean firstIsLabel) {
		_firstIsLabel = firstIsLabel;
	}

	public Point getSelectFrom() {
		return _selectFrom;
	}

	public void setSelectFrom(Point selectFrom) {
		_selectFrom = selectFrom;
	}

	public Point getSelectTo() {
		return _selectTo;
	}

	public void setSelectTo(Point selectTo) {
		_selectTo = selectTo;
	}

	public void addRow(Row row) {
		_rows.add(row);
	}

	public void addColumn(Column column) {
		_columns.add(column);
	}

	public void setValues(Values values) {
		_values = values;
	}

	public Values getValues() {
		return _values;
	}

	public Map<Integer, List<Comparable<?>>> getValuesAsMap() {
		HashMap<Integer, List<Comparable<?>>> map = new LinkedHashMap<Integer, List<Comparable<?>>>();
		List<Row> rows = getRows();
		int columnSize = getColumns().size();
		for (Row row : rows) {
			int rowIndex = row.getIndex();
			List<Comparable<?>> rowValues = new ArrayList<Comparable<?>>();
			map.put(rowIndex, rowValues);
			for (int i = 0; i < columnSize; i++) {
				Point point = new Point(i, rowIndex);
				Comparable<?> value = _values.getValue(point);
				rowValues.add(value);
			}

		}
		return map;
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_fileName = in.readUTF();
		_description = in.readUTF();
		_sheetIndex = in.readInt();
		_firstIsLabel = in.readBoolean();

		_documentType = DocumentType.valueOf(in.readUTF());
		int size = in.readInt();
		_rows.clear();
		for (int i = 0; i < size; i++) {
			Row row = new Row();
			row.readExternal(in);
			_rows.add(row);
		}

		size = in.readInt();
		_columns.clear();
		for (int i = 0; i < size; i++) {
			Column column = new Column();
			column.readExternal(in);
			_columns.add(column);
		}

		_selectFrom.readExternal(in);
		_selectTo.readExternal(in);

		// values does not need to deserialize
	}

	public void writeExternal(ObjectOutput out) throws IOException {

		// flat types
		out.writeUTF(_fileName);
		out.writeUTF(_description);
		out.writeInt(_sheetIndex);
		out.writeBoolean(_firstIsLabel);

		// objects
		out.writeUTF(_documentType.name());
		out.writeInt(_rows.size());
		for (Row row : _rows) {
			row.writeExternal(out);
		}
		out.writeInt(_columns.size());
		for (Column column : _columns) {
			column.writeExternal(out);
		}

		_selectFrom.writeExternal(out);
		_selectTo.writeExternal(out);

		// values does not need to serialize

	}

	public void setWorkbook(byte[] workBook) {
		_workBook = workBook;
	}

	public byte[] getWorkbook() {
		return _workBook;
	}

	@Override
	public int hashCode() {
		return _sheetIndex;
	}

	@Override
	public boolean equals(Object obj) {
		Sheet other = (Sheet) obj;
		return other.getSheetIndex() == _sheetIndex;
	}

}
