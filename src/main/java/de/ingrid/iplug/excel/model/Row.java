package de.ingrid.iplug.excel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row extends AbstractEntry {

	public Row() {
		super();
	}

	public Row(Values values, int rowIndex) {
		super(values, rowIndex);
		setLabel(rowIndex + 1 + "");
	}

	public Serializable getValue(int columnIndex) {
		Serializable ret = null;
		Iterator<Point> pointIterator = _values.getPointIterator();
		while (pointIterator.hasNext()) {
			Point point = (Point) pointIterator.next();
			if (point.getX() == columnIndex && point.getY() == getIndex()) {
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
			if (point.getY() == getIndex()) {
				list.add(_values.getValue(point));
			}
		}
		return list;
	}
}
