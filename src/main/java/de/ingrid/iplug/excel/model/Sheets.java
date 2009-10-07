package de.ingrid.iplug.excel.model;

import java.util.ArrayList;
import java.util.List;

public class Sheets {

	private List<Sheet> _sheets = new ArrayList<Sheet>();

	public List<Sheet> getSheets() {
		return _sheets;
	}

	public void addSheet(Sheet sheet) {
		_sheets.add(sheet);
	}
}
