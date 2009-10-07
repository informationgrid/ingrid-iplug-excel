package de.ingrid.iplug.excel.model;

public class Point {

	private int _x;

	private int _y;

	public Point(int x, int y) {
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public void setX(int x) {
		_x = x;
	}

	public int getY() {
		return _y;
	}

	public void setY(int y) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (_x != other._x)
			return false;
		if (_y != other._y)
			return false;
		return true;
	}

}
