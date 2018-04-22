package com.orioles.model;

import com.orioles.districtgeneration.Coordinate;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public class Precinct implements Cloneable {
    private String name;
    private int identifier;
    private CongressionalDistrict district;
    private List<Precinct> adjacentPrecincts;
    private List<Coordinate> coordinates;
    private Stats stats;

    @Transient
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

    public Precinct(List<Coordinate> coordinates){
        this.coordinates = coordinates;
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

    public void setAdjacentPrecincts(ArrayList<Precinct> adjacentPrecincts) {
        this.adjacentPrecincts = adjacentPrecincts;
    }

    Stats getStats() {
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

    public List<Coordinate> getCoordinates(){
            return coordinates;
    }
    
    public void setArea(double newArea){
        this.area = newArea;
    }
    
    public double getArea(){
        return this.area;
    }
}
