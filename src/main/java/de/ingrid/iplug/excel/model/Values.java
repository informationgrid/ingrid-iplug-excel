package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Values implements Externalizable, Iterable<Point> {

    private final SortedMap<Point, Comparable<? extends Object>> _values = new TreeMap<Point, Comparable<? extends Object>>();

	public void addValue(final Point point, final Comparable<? extends Object> value) {
		_values.put(point, value);
	}

	public Comparable<? extends Object> getValue(final Point point) {
		return _values.get(point);
	}

	public Comparable<? extends Object> getValue(final int x, final int y) {
		return _values.get(new Point(x, y));
	}

    @Override
    public Iterator<Point> iterator() {
        return _values.keySet().iterator();
    }

    public int getSize() {
        return _values.size();
    }

	@SuppressWarnings("unchecked")
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		_values.clear();
		final int size = in.readInt();
		for (int i = 0; i < size; i++) {
            final Point point = (Point) in.readObject();
            final Comparable<? extends Object> serializable = (Comparable<Object>) in.readObject();
			_values.put(point, serializable);
		}
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(_values.size());
        final Set<Point> set = _values.keySet();
        for (final Point point : set) {
            out.writeObject(point);
            out.writeObject(_values.get(point));
		}
	}
}
