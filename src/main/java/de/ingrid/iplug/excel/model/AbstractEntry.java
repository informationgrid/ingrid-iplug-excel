package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntry implements Externalizable {

	private String _label;
	private List<Filter> _filters = new ArrayList<Filter>();
	private FieldType _fieldType = FieldType.TEXT;
	private float _rank;
	private boolean _mapped;
	private boolean _excluded;
	private boolean _matchFilter = true;

	private int _index;

	public AbstractEntry() {
		// externalizable
	}

	public AbstractEntry(int index) {
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

	public void removeFilter(int index) {
		_filters.remove(index);
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

	/**
	 * if true then the entry will be indexed
	 * 
	 * @param addToIndex
	 */
	public void setMatchFilter(boolean addToIndex) {
		_matchFilter = addToIndex;
	}

	/**
	 * if true then the entry will be indexed
	 * 
	 * @param filtered
	 */
	public boolean isMatchFilter() {
		return _matchFilter;
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_label = in.readUTF();
		_excluded = in.readBoolean();
		_mapped = in.readBoolean();
		_matchFilter = in.readBoolean();
		_rank = in.readFloat();
		_index = in.readInt();
		_fieldType = FieldType.valueOf(in.readUTF());
		int size = in.readInt();
		_filters.clear();
		for (int i = 0; i < size; i++) {
			Filter filter = new Filter();
			filter.readExternal(in);
			_filters.add(filter);
		}
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(_label);
		out.writeBoolean(_excluded);
		out.writeBoolean(_mapped);
		out.writeBoolean(_matchFilter);
		out.writeFloat(_rank);
		out.writeInt(_index);
		out.writeUTF(_fieldType.name());
		out.writeInt(_filters.size());
		for (Filter filter : _filters) {
			filter.writeExternal(out);
		}

	}

	@Override
	public String toString() {
		return _label + "#" + _excluded + "#" + _mapped + "#" + _matchFilter;
	}
}
