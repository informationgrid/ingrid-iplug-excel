package de.ingrid.iplug.excel.model;

public class Column extends AbstractEntry {

	public static final String[] LABELS = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public Column() {
		super();
	}

	public Column(int columnIndex) {
		super(columnIndex);
		setLabel(getDefaultLabel());
	}

	public String getDefaultLabel() {
		if (getIndex() < 26) {
			return LABELS[getIndex()];
		}
		String firstChar = LABELS[(int) (Math.floor(getIndex() / 26)) - 1];
		String secondChar = LABELS[getIndex() % 26];
		return firstChar + secondChar;
	}

}
