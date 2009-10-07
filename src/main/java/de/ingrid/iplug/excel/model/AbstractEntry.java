package de.ingrid.iplug.excel.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntry {

	private int _index;
	private String _label;
	private List<Filter> _filters = new ArrayList<Filter>();
	private FieldType _fieldType;
	private float _rank;
	private boolean _mapped;
	private boolean _excluded;

	public int getIndex() {
		return _index;
	}

	public void setIndex(int columnIndex) {
		_index = columnIndex;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public List<Filter> getFilters() {
		return _filters;
	}

	public void addFilter(Filter filter) {
		_filters.add(filter);
	}

	public FieldType getFieldType() {
		return _fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		_fieldType = fieldType;
	}

	public float getRank() {
		return _rank;
	}

	public void setRank(float rank) {
		_rank = rank;
	}

	public boolean isMapped() {
		return _mapped;
	}

	public void setMapped(boolean mapped) {
		_mapped = mapped;
	}

	public boolean isExcluded() {
		return _excluded;
	}

	public void setExcluded(boolean excluded) {
		_excluded = excluded;
	}

}
