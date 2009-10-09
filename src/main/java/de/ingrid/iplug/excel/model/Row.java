package de.ingrid.iplug.excel.model;


public class Row extends AbstractEntry {

	public Row() {
		super();
	}

	public Row(int rowIndex) {
		super(rowIndex);
		setLabel(rowIndex + 1 + "");
	}

}
