package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Values implements Externalizable {

	private Map<Point, Comparable<? extends Object>> _values = new LinkedHashMap<Point, Comparable<? extends Object>>();

	public void addValue(Point point, Comparable<? extends Object> value) {
		_values.put(point, value);
	}

	public Comparable<? extends Object> getValue(Point point) {
		return _values.get(point);
	}

	public Comparable<? extends Object> getValue(int x, int y) {
		return _values.get(new Point(x, y));
	}

	public Iterator<Point> getPointIterator() {
		return _values.keySet().iterator();
	}

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_values.clear();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			Point point = new Point();
			point.readExternal(in);
			Comparable<? extends Object> serializable = (Comparable<Object>) in
					.readObject();
			_values.put(point, serializable);
		}
	}

	public int getSize() {
		return _values.size();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(_values.size());
		Set<Point> keySet = _values.keySet();
		for (Point point : keySet) {
			point.writeExternal(out);
			Comparable<? extends Object> serializable = _values.get(point);
			out.writeObject(serializable);
		}

	}

}
