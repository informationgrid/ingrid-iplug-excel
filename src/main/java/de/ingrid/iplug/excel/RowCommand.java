package de.ingrid.iplug.excel;

import java.util.ArrayList;
import java.util.List;

public class RowCommand {

	private List<String> _cells = new ArrayList<String>();

	public List<String> getCells() {
		return _cells;
	}

	public void setCells(List<String> cells) {
		_cells = cells;
	}

	public void addCell(String cell) {
		_cells.add(cell);
	}
}
