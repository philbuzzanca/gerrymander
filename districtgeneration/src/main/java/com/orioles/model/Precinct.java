package com.orioles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String name;

    @Column(name = "geojson", columnDefinition = "LONGTEXT")
    private String geojson;

    @Transient
    @JsonIgnore
    private CongressionalDistrict district; // contained within the embedded ID.

    @Transient
	@JsonIgnore
    private List<Precinct> adjacentPrecincts;

    @Transient
	@JsonIgnore
    private List<Coordinate> coordinates;

    @Transient
	@JsonIgnore
    private Stats stats;

    @Transient
	@JsonIgnore
    private boolean locked;

    @Transient
	@JsonIgnore
    private double area;

    @EmbeddedId
	@JsonIgnore
    private PrecinctId id;

    public Precinct() {
        name = "";
        adjacentPrecincts = new ArrayList<>();
        coordinates = new ArrayList<>();
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
        return id.getId();
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
