package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sheets implements Externalizable, Iterable<Sheet> {

	private final List<Sheet> _sheets = new ArrayList<Sheet>();

	public List<Sheet> getSheets() {
		return _sheets;
	}

    public Sheet getSheet(final int index) {
        for (final Sheet sheet : _sheets) {
            if (sheet.getSheetIndex() == index) {
                return sheet;
            }
        }
        return null;
    }

	public void addSheet(final Sheet sheet) {
		_sheets.add(sheet);
	}

	public boolean existsSheet(final Sheet sheet) {
		return _sheets.contains(sheet);
	}

	public void removeSheet(final Sheet sheet) {
		_sheets.remove(sheet);
	}

    public Iterator<Sheet> iterator() {
        return _sheets.iterator();
    }

	public void readExternal(final ObjectInput in) throws IOException,
			ClassNotFoundException {
		_sheets.clear();
		final int size = in.readInt();
		for (int i = 0; i < size; i++) {
            final Sheet sheet = (Sheet) in.readObject();
            _sheets.add(sheet);
		}
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(_sheets.size());
		for (final Sheet sheet : _sheets) {
            out.writeObject(sheet);
		}
	}
}
