package com.orioles.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Precinct implements Cloneable {
    private String name;
    private int identifier;
    private CongressionalDistrict district;
    private List<Precinct> adjacentPrecincts;
	private List<Point2D.Double> coordinates;
    private Stats stats;
    private boolean locked;

    public Precinct(){
        name = "";
        identifier = -1;
        district = null;
        adjacentPrecincts = new ArrayList<>();
        coordinates = new ArrayList<>();
        stats = null;
        locked = false;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public CongressionalDistrict getDistrict() {
        return district;
    }

    public void setDistrict(CongressionalDistrict district) {
        this.district = district;
    }

    public List<Precinct> getAdjacentPrecincts() {
        return adjacentPrecincts;
    }

    public void setAdjacentPrecincts(List<Precinct> adjacentPrecincts) {
        this.adjacentPrecincts = adjacentPrecincts;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isOnBorder(){
        return false;
    }

	public void setCoordinates(List<Point2D.Double> coordinates){
		this.coordinates = coordinates;
	}

	public List<Point2D.Double> getCoordinates(){
		return coordinates;
	}
}
