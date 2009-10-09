package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntry implements Externalizable {

	private String _label;
	private List<Filter> _filters = new ArrayList<Filter>();
	private FieldType _fieldType;
	private float _rank;
	private boolean _mapped;
	private boolean _excluded;

	protected Values _values;
	private int _index;

	public AbstractEntry() {
		// extermalizable
	}

	public AbstractEntry(Values values, int index) {
		_values = values;
		_index = index;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
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
	
	public void removeFilter(Filter filter) {
		_filters.remove(filter);
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
	
	public boolean getIsMapped() {
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

	public abstract Serializable getValue(int index);

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_label = in.readUTF();

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(_label);

	}
}
