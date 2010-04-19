package de.ingrid.iplug.excel.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Class to define point objects.
 *
 */
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

	/**
	 * Get x of point.
	 * 
	 * @return
	 * 		X value of point.
	 * 
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Set x of point.
	 * 
	 * @param x
	 */
	public void setX(final int x) {
		_x = x;
	}

	/**
	 * Get y of point
	 * 
	 * @return
	 * 		Y value of point. 
	 */
	public int getY() {
		return _y;
	}

	/**
	 * Set y of point.
	 * @param y
	 */
	public void setY(final int y) {
		_y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
    public int compareTo(final Point other) {
        final int x = _x - other.getX();
        if (x == 0) {
            return _y - other.getY();
        }
        return x;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /* (non-Javadoc)
     * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
     */
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		_x = in.readInt();
		_y = in.readInt();
	}

	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeInt(_x);
		out.writeInt(_y);
	}
}
