package de.ingrid.iplug.excel.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Matrix {

	private Map<Point, Serializable> _values = new HashMap<Point, Serializable>();

	public void addValue(Point point, Serializable value) {
		_values.put(point, value);
	}

	public Serializable getValue(Point point) {
		return _values.get(point);
	}

	public Iterator<Point> getPointIterator() {
		return _values.keySet().iterator();
	}
}
