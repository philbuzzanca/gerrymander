package com.orioles.districtgeneration;

public class Coordinate {
	private double x;
	private double y;

	public Coordinate() {
		x = 0;
		y = 0;
	}

	public Coordinate(double xVal, double yVal) {
		this.x = xVal;
		this.y = yVal;
	}

	double getX() {
		return this.x;
	}

	double getY() {
		return this.y;
	}

	void setX(double xValue) {
		this.x = xValue;
	}

	void setY(double yValue) {
		this.y = yValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Coordinate otherCoordinate = (Coordinate) o;
		return otherCoordinate.x == this.x && otherCoordinate.y == this.y;
	}
}
