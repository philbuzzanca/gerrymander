package com.orioles.districtgeneration;

import com.orioles.constants.Constants;
import com.orioles.helper_model.Pair;

import java.io.Serializable;

public final class Edge extends Pair<Coordinate, Coordinate> implements Serializable {
	private double distance;

    public Edge(Coordinate p1, Coordinate p2){
    	super(p1, p2);
    }
    
    public Coordinate getP1(){
        return getKey();
    }
    
    private Coordinate getP2(){
        return getValue();
    }

    private boolean equals(Edge otherEdge){
        return this.getP1().equals(otherEdge.getP1()) && this.getP2().equals(otherEdge.getP2())
                || this.getP1().equals(otherEdge.getP2()) && this.getP2().equals(otherEdge.getP1());
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return this.equals((Edge) o);
	}

	/**
	 * Computes distance via haversine formula
	 * @return distance in km
	 */
	public double calculateDistance() {
		if (distance != 0)
			return distance;

		double latDistance = Math.toRadians(getP2().getY() - getP1().getY());
		double lonDistance = Math.toRadians(getP2().getX() - getP1().getX());

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(getP1().getY())) * Math.cos(Math.toRadians(getP2().getY()))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		return distance = Constants.EARTH_RADIUS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}
}
