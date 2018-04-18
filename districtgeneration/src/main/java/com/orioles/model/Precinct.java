package com.orioles.model;

import java.util.ArrayList;

public class Precinct implements Cloneable {
    private String name;
    private int identifier;
    private CongressionalDistrict district;
    private ArrayList<Precinct> adjacentPrecincts;
    private ArrayList<Coordinate> coordinates;
    private Stats stats;
    private boolean locked;
    private double area;

    public Precinct(){
        name = "";
        identifier = -1;
        district = null;
        adjacentPrecincts = new ArrayList<>();
        coordinates = new ArrayList<>();
        stats = null;
        locked = false;
        area = 0;
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

    public ArrayList<Precinct> getAdjacentPrecincts() {
        return adjacentPrecincts;
    }

    public void setAdjacentPrecincts(ArrayList<Precinct> adjacentPrecincts) {
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

    public void setCoordinates(ArrayList<Coordinate> coordinates){
            this.coordinates = coordinates;
    }

    public ArrayList<Coordinate> getCoordinates(){
            return coordinates;
    }
    
    public void setArea(double newArea){
        this.area = newArea;
    }
    
    public double getArea(){
        return this.area;
    }
}
