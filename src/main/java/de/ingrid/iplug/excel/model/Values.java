package de.ingrid.iplug.excel.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Values {

	private Map<Point, Serializable> _values = new LinkedHashMap<Point, Serializable>();

	public void addValue(Point point, Serializable value) {
		_values.put(point, value);
	}

	public Serializable getValue(Point point) {
		return _values.get(point);
	}

	public Serializable getValue(int x, int y) {
		return _values.get(new Point(x, y));
	}
	
	public Iterator<Point> getPointIterator() {
		return _values.keySet().iterator();
	}
	
	
}
