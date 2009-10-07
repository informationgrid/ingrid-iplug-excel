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
		// TODO Auto-generated method stub

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub

	}
}
