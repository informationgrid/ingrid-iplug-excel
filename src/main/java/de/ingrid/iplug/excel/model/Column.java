package de.ingrid.iplug.excel.model;

public class Column extends AbstractEntry {

	public static final String[] LABELS = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public Column() {
		super();
	}

	public Column(final int columnIndex) {
		super(columnIndex);
        setLabel(getDefaultLabel(getIndex()));
	}

    public static String getDefaultLabel(int index) {
	    String label = "";
        while (index >= 0) {
	        final int pos = index % 26;
	        label = LABELS[pos] + label;
	        index = index/26 - 1;
	    }
	    return label;
	}

}
