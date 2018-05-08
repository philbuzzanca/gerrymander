package com.orioles.districtgeneration;

import com.orioles.helper_model.Pair;

public final class Coordinate extends Pair<Double, Double> {
	public Coordinate(double xVal, double yVal) {
		super(xVal, yVal);
	}

	double getX() {
		return getKey();
	}

	double getY() {
		return getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Coordinate otherCoordinate = (Coordinate) o;
		return otherCoordinate.getX() == this.getY() && otherCoordinate.getY() == this.getY();
	}
}
