package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class Sheets implements Externalizable {

	private List<Sheet> _sheets = new ArrayList<Sheet>();

	public List<Sheet> getSheets() {
		return _sheets;
	}

	public void addSheet(Sheet sheet) {
		_sheets.add(sheet);
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_sheets.clear();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			Sheet sheet = new Sheet();
			sheet.readExternal(in);
			_sheets.add(sheet);
		}
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(_sheets.size());
		for (Sheet sheet : _sheets) {
			sheet.writeExternal(out);
		}
	}
}
