/*
 * **************************************************-
 * ingrid-iplug-excel
 * ==================================================
 * Copyright (C) 2014 - 2024 wemove digital solutions GmbH
 * ==================================================
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
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
import java.util.Iterator;
import java.util.List;

/**
 * Creation sheets.
 *
 */
public class Sheets implements Externalizable, Iterable<Sheet> {

	private final List<Sheet> _sheets = new ArrayList<Sheet>();

	public List<Sheet> getSheets() {
		return _sheets;
	}

    /** Get sheet.
     * @param index
     * @return
     * 		Get existing sheet.
     */
    public Sheet getSheet(final int index) {
        for (final Sheet sheet : _sheets) {
            if (sheet.getSheetIndex() == index) {
                return sheet;
            }
        }
        return null;
    }

	/**
	 * Add sheet.
	 * 
	 * @param sheet
	 */
	public void addSheet(final Sheet sheet) {
		_sheets.add(sheet);
	}

	/**
	 * Check if sheet exists.
	 * 
	 * @param sheet
	 * @return
	 * 		true if sheet exists.
	 */
	public boolean existsSheet(final Sheet sheet) {
		return _sheets.contains(sheet);
	}

	/**
	 * Remove sheet.
	 * 
	 * @param sheet
	 */
	public void removeSheet(final Sheet sheet) {
		_sheets.remove(sheet);
	}

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Sheet> iterator() {
        return _sheets.iterator();
    }

	/* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	public void readExternal(final ObjectInput in) throws IOException,
			ClassNotFoundException {
		_sheets.clear();
		final int size = in.readInt();
		for (int i = 0; i < size; i++) {
            final Sheet sheet = (Sheet) in.readObject();
            _sheets.add(sheet);
		}
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(_sheets.size());
		for (final Sheet sheet : _sheets) {
            out.writeObject(sheet);
		}
	}
}
