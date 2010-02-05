package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Point implements Externalizable, Comparable<Point> {

	private int _x = -1;

	private int _y = -1;

	public Point() {
		// externalizable
	}

	public Point(final int x, final int y) {
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public void setX(final int x) {
		_x = x;
	}

	public int getY() {
		return _y;
	}

	public void setY(final int y) {
		_y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

	@Override
    public int compareTo(final Point other) {
        final int x = _x - other.getX();
        if (x == 0) {
            return _y - other.getY();
        }
        return x;
    }

    @Override
	public boolean equals(final Object obj) {
		if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            final Point other = (Point) obj;
            if (_x != other._x || _y != other._y) {
                return false;
            }
        }
		return true;
	}

    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		_x = in.readInt();
		_y = in.readInt();
	}

	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(_x);
		out.writeInt(_y);
	}
}
