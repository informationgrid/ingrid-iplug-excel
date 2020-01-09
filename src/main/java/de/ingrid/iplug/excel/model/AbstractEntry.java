/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2020 wemove digital solutions GmbH
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
import java.util.List;

import de.ingrid.admin.mapping.FieldType;
import de.ingrid.admin.mapping.Filter;

public abstract class AbstractEntry implements Externalizable {

    private int _index;
	private String _label;

    private boolean _excluded;

    private boolean _mapped;
	private float _rank;
	private boolean _matchFilter = true;

    private FieldType _fieldType = FieldType.TEXT;

    private final List<Filter> _filters = new ArrayList<Filter>();

	public AbstractEntry() {
		// externalizable
	}

	public AbstractEntry(final int index) {
		_index = index;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(final int index) {
		_index = index;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(final String label) {
		_label = label;
	}

	public List<Filter> getFilters() {
		return _filters;
	}

	public void addFilter(final Filter filter) {
		_filters.add(filter);
	}

	public void removeFilter(final Filter filter) {
		_filters.remove(filter);
	}

	public void removeFilter(final int index) {
		_filters.remove(index);
	}

	public FieldType getFieldType() {
		return _fieldType;
	}

	public void setFieldType(final FieldType fieldType) {
		_fieldType = fieldType;
	}

	public float getRank() {
		return _rank;
	}

	public void setRank(final float rank) {
		_rank = rank;
	}

	public boolean isMapped() {
		return _mapped;
	}

	public boolean getIsMapped() {
		return _mapped;
	}

	public void setMapped(final boolean mapped) {
		_mapped = mapped;
	}

	public boolean isExcluded() {
		return _excluded;
	}

	public void setExcluded(final boolean excluded) {
		_excluded = excluded;
	}

	/**
	 * if true then the entry will be indexed
	 *
	 * @param addToIndex
	 */
	public void setMatchFilter(final boolean addToIndex) {
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

    /* (non-Javadoc)
     * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
     */
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        // flat types
        _index = in.readInt();
		_label = in.readUTF();
		_excluded = in.readBoolean();
		_mapped = in.readBoolean();
        _rank = in.readFloat();
		_matchFilter = in.readBoolean();

        // objects
		_fieldType = FieldType.valueOf(in.readUTF());
        _filters.clear();
		final int size = in.readInt();
		for (int i = 0; i < size; i++) {
            final Filter filter = (Filter) in.readObject();
			_filters.add(filter);
		}
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(final ObjectOutput out) throws IOException {
        // flat types
        out.writeInt(_index);
		out.writeUTF(_label);
		out.writeBoolean(_excluded);
		out.writeBoolean(_mapped);
        out.writeFloat(_rank);
		out.writeBoolean(_matchFilter);

        // objects
		out.writeUTF(_fieldType.name());
		out.writeInt(_filters.size());
		for (final Filter filter : _filters) {
            out.writeObject(filter);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return _label + "#" + _excluded + "#" + _mapped + "#" + _matchFilter;
	}
}
