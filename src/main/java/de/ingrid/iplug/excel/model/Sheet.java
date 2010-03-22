package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Sheet implements Externalizable {

	private String _fileName;
	private String _description;
	private int _sheetIndex;
    private boolean _firstIsLabel = false;
    private boolean _existing = false;
    private DocumentType _documentType = DocumentType.ROW;
	private SortedMap<Integer, Column> _columns = new TreeMap<Integer, Column>();
	private SortedMap<Integer, Row> _rows = new TreeMap<Integer, Row>();
    private Point _selectedFrom = new Point();
    private Point _selectedTo = new Point();
	private transient Values _values = new Values();
	private transient byte[] _workBook;

	public String getFileName() {
		return _fileName;
	}

	public void setFileName(final String fileName) {
		_fileName = fileName;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(final String description) {
		_description = description;
	}

	public int getSheetIndex() {
		return _sheetIndex;
	}

	public void setSheetIndex(final int sheetIndex) {
		_sheetIndex = sheetIndex;
	}

    public Collection<Column> getColumns() {
        return _columns.values();
	}

    public Collection<Column> getVisibleColumns() {
        final List<Column> list = new ArrayList<Column>();
        for (final Column column : _columns.values()) {
            if (!column.isExcluded()) {
                list.add(column);
            }
        }
        return list;
    }

    public Collection<Column> getExcludedColumns() {
        final List<Column> list = new ArrayList<Column>();
        for (final Column column : _columns.values()) {
            if (column.isExcluded()) {
                list.add(column);
            }
        }
        return list;
    }

    public SortedMap<Integer, Column> getColumnsMap() {
        return _columns;
    }

    public void setColumns(final SortedMap<Integer, Column> columns) {
		_columns = columns;
	}

    public Column getColumn(final int index) {
        return _columns.get(index);
    }

    public int getLastColumnIndex() {
        return _columns.lastKey();
    }

    public Column removeColumn(final int index) {
        return _columns.remove(index);
    }

    public Collection<Row> getRows() {
        return _rows.values();
	}

    public Collection<Row> getVisibleRows() {
        final List<Row> list = new ArrayList<Row>();
        for (final Row row : _rows.values()) {
            if (!row.isExcluded()) {
                list.add(row);
            }
        }
        return list;
    }

    public Collection<Row> getExcludedRows() {
        final List<Row> list = new ArrayList<Row>();
        for (final Row row : _rows.values()) {
            if (row.isExcluded()) {
                list.add(row);
            }
        }
        return list;
    }

    public SortedMap<Integer, Row> getRowsMap() {
        return _rows;
    }

    public void setRows(final SortedMap<Integer, Row> rows) {
		_rows = rows;
	}

    public Row getRow(final int index) {
        return _rows.get(index);
    }

    public int getLastRowIndex() {
        return _rows.lastKey();
    }

    public Row removeRow(final int index) {
        return _rows.remove(index);
    }

	public DocumentType getDocumentType() {
		return _documentType;
	}

	public void setDocumentType(final DocumentType documentType) {
		_documentType = documentType;
	}

	public boolean isFirstIsLabel() {
		return _firstIsLabel;
	}

	public void setFirstIsLabel(final boolean firstIsLabel) {
        if (_firstIsLabel != firstIsLabel) {
            // handle firstIsLabel
            if (_documentType.equals(DocumentType.ROW)) {
                // we map columns to index fields, a row is a doc
                final Row firstRow = _rows.get(_rows.firstKey());
                firstRow.setExcluded(firstIsLabel);

                // set the label
                for (final Column column : _columns.values()) {
                    if (firstIsLabel) {
                        final Comparable<? extends Object> value = getValue(column.getIndex(), firstRow.getIndex());
                        column.setLabel(value == null ? Column.getDefaultLabel(column.getIndex()) : value.toString());
                    } else {
                        column.setLabel(Column.getDefaultLabel(column.getIndex()));
                    }
                }
            } else if (_documentType.equals(DocumentType.COLUMN)) {
                // we map rows to index fields, a column is a doc
                final Column firstColumn = _columns.get(_columns.firstKey());
                firstColumn.setExcluded(firstIsLabel);

                // set the label
                for (final Row row : _rows.values()) {
                    if (firstIsLabel) {
                        final Comparable<? extends Object> value = getValue(firstColumn.getIndex(), row.getIndex());
                        row.setLabel(value == null ? "" + row.getIndex() : value.toString());
                    } else {
                        row.setLabel("" + row.getIndex());
                    }
                }
            }
            _firstIsLabel = firstIsLabel;
        }
	}

	public void addRow(final Row row) {
        _rows.put(row.getIndex(), row);
	}

	public void addColumn(final Column column) {
        _columns.put(column.getIndex(), column);
	}

	public void setValues(final Values values) {
		_values = values;
	}

	public Values getValues() {
		return _values;
	}

    public Comparable<? extends Object> getValue(final Point point) {
        return _values.getValue(point);
    }

    public Comparable<? extends Object> getValue(final int x, final int y) {
        return _values.getValue(x, y);
    }

	public Map<Integer, List<Comparable<?>>> getValuesAsMap() {
		final HashMap<Integer, List<Comparable<?>>> map = new LinkedHashMap<Integer, List<Comparable<?>>>();
		int rowIndex = 0;
        for (final Row row : getRows()) {
			final List<Comparable<?>> rowValues = new ArrayList<Comparable<?>>();
			map.put(rowIndex, rowValues);
            for (final Column col : getColumns()) {
			    final Point point = new Point(col.getIndex(), row.getIndex());
			    final Comparable<?> value = _values.getValue(point);
                rowValues.add(value);
			}
			rowIndex++;
		}
		return map;
	}

    public List<List<Comparable<?>>> getVisibleValues() {
        final List<List<Comparable<?>>> map = new ArrayList<List<Comparable<?>>>();
        for (final Row row : getVisibleRows()) {
            final List<Comparable<?>> rowValues = new ArrayList<Comparable<?>>();
            for (final Column column : getVisibleColumns()) {
                final Comparable<?> value = _values.getValue(column.getIndex(), row.getIndex());
                rowValues.add(value);
            }
            map.add(rowValues);
        }
        return map;
    }

    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        // flat types
		_fileName = in.readUTF();
		_description = in.readUTF();
		_sheetIndex = in.readInt();
		_firstIsLabel = in.readBoolean();
        _existing = in.readBoolean();

        // objects
		_documentType = DocumentType.valueOf(in.readUTF());
        _columns.clear();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
            final int index = in.readInt();
            final Column column = (Column) in.readObject();
            _columns.put(index, column);
		}
		size = in.readInt();
        _rows.clear();
		for (int i = 0; i < size; i++) {
            final int index = in.readInt();
            final Row row = (Row) in.readObject();
            _rows.put(index, row);
		}

        // select area
        _selectedFrom = (Point) in.readObject();
        _selectedTo = (Point) in.readObject();

		// values does not need to deserialize
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		// flat types
		out.writeUTF(_fileName);
		out.writeUTF(_description);
		out.writeInt(_sheetIndex);
		out.writeBoolean(_firstIsLabel);
        out.writeBoolean(_existing);

		// objects
		out.writeUTF(_documentType.name());
        out.writeInt(_columns.size());
        for (final Column column : _columns.values()) {
            out.writeInt(column.getIndex());
            out.writeObject(column);
        }
		out.writeInt(_rows.size());
        for (final Row row : _rows.values()) {
            out.writeInt(row.getIndex());
            out.writeObject(row);
		}

        // select area
        out.writeObject(_selectedFrom);
        out.writeObject(_selectedTo);

		// values does not need to serialize
	}

	public void setWorkbook(final byte[] workBook) {
		_workBook = workBook;
	}

	public byte[] getWorkbook() {
		return _workBook;
	}

	@Override
	public int hashCode() {
		return _fileName.hashCode() + _sheetIndex;
	}

	@Override
	public boolean equals(final Object obj) {
		final Sheet other = (Sheet) obj;
        return other.getSheetIndex() == _sheetIndex && other.getFileName().equals(_fileName);
	}

    public void setFrom(final int x, final int y) {
        _selectedFrom.setX(x);
        _selectedFrom.setY(y);
    }

    public Point getSelectedFrom() {
        return _selectedFrom;
    }

    public Column getFromColumn() {
        return getColumn(_selectedFrom.getX());
    }

    public Row getFromRow() {
        return getRow(_selectedFrom.getY());
    }

    public void setTo(final int x, final int y) {
        _selectedTo.setX(x);
        _selectedTo.setY(y);
    }

    public Point getSelectedTo() {
        return _selectedTo;
    }

    public Column getToColumn() {
        return getColumn(_selectedTo.getX());
    }

    public Row getToRow() {
        return getRow(_selectedTo.getY());
    }

    public boolean getSelected() {
        return _selectedFrom.getX() > -1 && _selectedFrom.getY() > -1 && _selectedTo.getX() > -1
                && _selectedTo.getY() > -1;
    }

    public List<Comparable<? extends Object>> getValuesOfColumn(final int index) {
        final List<Comparable<? extends Object>> list = new ArrayList<Comparable<? extends Object>>();
        for (final Point point : _values) {
            if (point.getX() == index) {
                list.add(_values.getValue(point));
            }
        }
        return list;
    }

    public List<Comparable<? extends Object>> getValuesOfRow(final int index) {
        final List<Comparable<? extends Object>> list = new ArrayList<Comparable<? extends Object>>();
        for (final Point point : _values) {
            if (point.getY() == index) {
                list.add(_values.getValue(point));
            }
        }
        return list;
    }

    public void setExisting(final boolean existing) {
        _existing = existing;
    }

    public boolean isExisting() {
        return _existing;
    }
}
