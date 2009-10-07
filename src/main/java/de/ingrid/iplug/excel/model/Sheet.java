package de.ingrid.iplug.excel.model;

import java.util.ArrayList;
import java.util.List;

public class Sheet {

	private String _fileName;
	private String _description;
	private int _sheetIndex;
	private List<Column> _columns = new ArrayList<Column>();
	private List<Row> _rows = new ArrayList<Row>();
	private DocumentType _documentType;
	private boolean _firstIsLabel;
	private Point _selectFrom;
	private Point _selectTo;
	private Values _values;

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

}
