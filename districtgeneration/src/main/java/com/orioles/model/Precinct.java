package com.orioles.model;

import com.google.gson.Gson;
import com.orioles.districtgeneration.Coordinate;
import java.io.Serializable;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Precinct implements Cloneable, Serializable {

    @Autowired
    @Transient
    private Gson gson;
    @Transient
    private int identifier;
    private String name;
    @Column(name = "geojson", columnDefinition = "LONGTEXT")
    private String geojson;
    @Transient
    private CongressionalDistrict district; // contained within the embedded ID.
    @Transient
    private List<Precinct> adjacentPrecincts;
    @Transient
    private List<Coordinate> coordinates;
    @Transient
    private Stats stats;
    @Transient
    private boolean locked;
    @Transient
    private double area;
    @EmbeddedId
    private PrecinctId id;

    public Precinct() {
        name = "";
        id = null;
        district = null;
        adjacentPrecincts = new ArrayList<>();
        coordinates = new ArrayList<>();
        stats = null;
        locked = false;
        area = 0;
    }

    public Precinct(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getGeojson() {
        return geojson;
    }

    public void setGeojson(String geojson) {
        this.geojson = geojson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrecinctId getId() {
        return id;
    }

    public void setId(PrecinctId id) {
        this.id = id;
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

    public boolean isOnBorder() {
        return false;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setArea(double newArea) {
        this.area = newArea;
    }

    public double getArea() {
        return this.area;
    }
}
