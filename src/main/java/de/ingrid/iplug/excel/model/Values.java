package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Values implements Externalizable {

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
	

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		_values.clear();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			Point point = new Point();
			point.readExternal(in);
			Serializable serializable = (Serializable) in.readObject();
			_values.put(point, serializable);
		}

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(_values.size());
		Set<Point> keySet = _values.keySet();
		for (Point point : keySet) {
			point.writeExternal(out);
			Serializable serializable = _values.get(point);
			out.writeObject(serializable);
		}

	}

}
