package de.ingrid.iplug.excel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Column extends AbstractEntry {

	public static final String[] LABELS = new String[] { "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public Column(Values values, int columnIndex) {
		super(values, columnIndex);
		setLabel(getDefaultLabel());
	}

	private String getDefaultLabel() {
		if (getIndex() < 26) {
			return LABELS[getIndex()];
		}

		String firstChar = LABELS[(int) (Math.floor(getIndex() / 26)) - 1];
		String secondChar = LABELS[getIndex() % 26];
		return firstChar + secondChar;

	}

	public Serializable getValue(int rowIndex) {
		Serializable ret = null;
		Iterator<Point> pointIterator = _values.getPointIterator();
		while (pointIterator.hasNext()) {
			Point point = (Point) pointIterator.next();
			if (point.getX() == getIndex() && point.getY() == rowIndex) {
				ret = _values.getValue(point);
			}
		}
		return ret;
	}

	public List<Serializable> getValues() {
		List<Serializable> list = new ArrayList<Serializable>();
		Iterator<Point> pointIterator = _values.getPointIterator();
		while (pointIterator.hasNext()) {
			Point point = (Point) pointIterator.next();
			if (point.getX() == getIndex()) {
				list.add(_values.getValue(point));
			}
		}
		return list;
	}

}
