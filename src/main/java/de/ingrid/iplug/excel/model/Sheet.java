/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * **************************************************#
 */
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

/**
 * Creation of a sheet. 
 *
 */
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

	/**
	 * Get filename.
	 * 
	 * @return
	 * 		Filename.
	 */
	public String getFileName() {
		return _fileName;
	}

	/**
	 * Set filename.
	 * 
	 * @param fileName
	 */
	public void setFileName(final String fileName) {
		_fileName = fileName;
	}

	/**
	 * Get descriptor.
	 * 
	 * @return 
	 * 		Descriptor.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Set descriptor.
	 * 
	 * @param description
	 */
	public void setDescription(final String description) {
		_description = description;
	}

	/**
	 * Get sheet index.
	 * 
	 * @return
	 * 		Existing sheet index.
	 */
	public int getSheetIndex() {
		return _sheetIndex;
	}

	/**
	 * Set sheet index.
	 * 
	 * @param sheetIndex
	 */
	public void setSheetIndex(final int sheetIndex) {
		_sheetIndex = sheetIndex;
	}

    /**
     * Get columns.
     *  
     * @return
     * 		Columns.
     */
    public Collection<Column> getColumns() {
        return _columns.values();
	}

    /**
     * Get visible columns.
     * 
     * @return
     * 		Visible columns.
     * 
     */
    public Collection<Column> getVisibleColumns() {
        final List<Column> list = new ArrayList<Column>();
        for (final Column column : _columns.values()) {
            if (!column.isExcluded()) {
                list.add(column);
            }
        }
        return list;
    }

    /**
     * Get excluded columns.
     * 
     * @return
     * 		Excluded columns.
     */
    public Collection<Column> getExcludedColumns() {
        final List<Column> list = new ArrayList<Column>();
        for (final Column column : _columns.values()) {
            if (column.isExcluded()) {
                list.add(column);
            }
        }
        return list;
    }

    /**
     * Get Columns as map.
     * 
     * @return
     * 		Map with columns.
     * 
     */
    public SortedMap<Integer, Column> getColumnsMap() {
        return _columns;
    }

    /**
     * Set Columns as map.
     * 
     * @param columns
     */
    public void setColumns(final SortedMap<Integer, Column> columns) {
		_columns = columns;
	}

    /**
     * Get Column with index.
     * 
     * @param index
     * @return
     * 		Column.
     */
    public Column getColumn(final int index) {
        return _columns.get(index);
    }

    /**
     * Get last column.
     * 
     * @return
     * 		Column.
     */
    public int getLastColumnIndex() {
        return _columns.lastKey();
    }

    /**
     * Remove column at index.
     * 
     * @param index
     * @return
     * 		Remove column.
     */
    public Column removeColumn(final int index) {
        return _columns.remove(index);
    }

    /**
     * Get row.
     * 
     * @return
     * 		Row values.
     */
    public Collection<Row> getRows() {
        return _rows.values();
	}

    /**
     * Get visible rows.
     * 
     * @return
     * 		List of rows.
     */
    public Collection<Row> getVisibleRows() {
        final List<Row> list = new ArrayList<Row>();
        for (final Row row : _rows.values()) {
            if (!row.isExcluded()) {
                list.add(row);
            }
        }
        return list;
    }

    /** 
     * Get excluded rows.
     * 
     * @return
     * 		List of excluded rows.
     */
    public Collection<Row> getExcludedRows() {
        final List<Row> list = new ArrayList<Row>();
        for (final Row row : _rows.values()) {
            if (row.isExcluded()) {
                list.add(row);
            }
        }
        return list;
    }

    /**
     * Get row as map
     * 
     * @return
     * 		Map with rows.
     * 
     */
    public SortedMap<Integer, Row> getRowsMap() {
        return _rows;
    }

    /**
     * Set a map of rows.
     * 
     * @param rows
     */
    public void setRows(final SortedMap<Integer, Row> rows) {
		_rows = rows;
	}

    /**
     * Get row at index.
     * 
     * @param index
     * @return
     * 		Row at index.
     */
    public Row getRow(final int index) {
        return _rows.get(index);
    }

    /** 
     * Get last row.
     * 
     * @return
     * 		Last row.
     * 
     */
    public int getLastRowIndex() {
        return _rows.lastKey();
    }

    /**
     * Remove row at index.
     * 
     * @param index
     * @return
     * 		Remove row at index.
     * 
     */
    public Row removeRow(final int index) {
        return _rows.remove(index);
    }

	/**
	 * Get document type.
	 * 
	 * @return
	 * 		Document type.
	 */
	public DocumentType getDocumentType() {
		return _documentType;
	}

	/**
	 * Set document type.
	 * 
	 * @param documentType
	 */
	public void setDocumentType(final DocumentType documentType) {
		_documentType = documentType;
	}

	/**
	 * Check if first is label.
	 * 
	 * @return
	 * 		true if first is label.
	 */
	public boolean isFirstIsLabel() {
		return _firstIsLabel;
	}

	/**
	 * Set first is label.
	 * 
	 * @param firstIsLabel
	 */
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

	/**
	 * Add row.
	 * 
	 * @param row
	 */
	public void addRow(final Row row) {
        _rows.put(row.getIndex(), row);
	}

	/**
	 * Add column.
	 * 
	 * @param column
	 */
	public void addColumn(final Column column) {
        _columns.put(column.getIndex(), column);
	}

	/**
	 * Set values.
	 * 
	 * @param values
	 */
	public void setValues(final Values values) {
		_values = values;
	}

	/**
	 * Get values.
	 * 
	 * @return
	 *		Values. 
	 * 
	 */
	public Values getValues() {
		return _values;
	}

    /**
     * Get values.
     * 
     * @param point
     * @return
     * 		Values.
     */
    public Comparable<? extends Object> getValue(final Point point) {
        return _values.getValue(point);
    }

    /**
     * Get values.
     * 
     * @param x
     * @param y
     * @return
     * 		Values.
     */
    public Comparable<? extends Object> getValue(final int x, final int y) {
        return _values.getValue(x, y);
    }

	/**
	 * Get values as map.
	 * 
	 * @return
	 * 		Map with values.
	 */
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

    /**
     * Get visible values.
     * 
     * @return
     * 		Visible values as list. 
     */
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

    /* (non-Javadoc)
     * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
     */
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

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
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

	/**
	 * Set work book.
	 * 
	 * @param workBook
	 */
	public void setWorkbook(final byte[] workBook) {
		_workBook = workBook;
	}

	/**
	 * Get workbook.
	 * 
	 * @return
	 * 		Work book as byte.
	 * 
	 */
	public byte[] getWorkbook() {
		return _workBook;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return _fileName.hashCode() + _sheetIndex;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		final Sheet other = (Sheet) obj;
        return other.getSheetIndex() == _sheetIndex && other.getFileName().equals(_fileName);
	}

    /**
     * Set selected x and y.
     * 
     * @param x
     * @param y
     */
    public void setFrom(final int x, final int y) {
        _selectedFrom.setX(x);
        _selectedFrom.setY(y);
    }

    /**
     * Get x and y of selection.
     * 
     * @return
     * 		Point with x and y.
     * 
     */
    public Point getSelectedFrom() {
        return _selectedFrom;
    }

    /**
     * Get column from selected x.
     * 
     * @return
     * 		Column of selected x.
     * 
     */
    public Column getFromColumn() {
        return getColumn(_selectedFrom.getX());
    }

    /**
     * Get row from selected y.
     * 
     * @return
     * 		Row of selected y.
     */
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
