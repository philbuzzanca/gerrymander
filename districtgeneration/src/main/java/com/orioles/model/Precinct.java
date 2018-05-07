package com.orioles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.helper_model.Polygon;

import java.io.Serializable;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Precinct implements Cloneable, Serializable {
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
    private List<Polygon> coordinates;
    @Transient
	@JsonIgnore
    private Stats stats;
    @Transient
	@JsonIgnore
    private boolean locked;
    @JsonIgnore
    private double area;
    @EmbeddedId
	@JsonIgnore
    private PrecinctId id;
    @JsonIgnore
	private int border;

    public Precinct() {
    	name = "";
        adjacentPrecincts = new ArrayList<>();
        coordinates = new ArrayList<>();
        area = 0;
    }

    public Precinct(List<Polygon> coordinates) {
        this.coordinates = coordinates;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeojson() {
        return geojson;
    }

    public void setGeojson(String geojson) {
        this.geojson = geojson;
    }

    public PrecinctId getId() {
        return id;
    }

    public void setId(PrecinctId id) {
        this.id = id;
    }

    public int getCD() {
    	return id.getCd();
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

    public void setAdjacentPrecincts(ArrayList<Precinct> adjPrecincts) {
        this.adjacentPrecincts = adjPrecincts;
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

	public void setBorder(int border) {
		this.border = border;
	}

	public boolean getBorder() {
        return border != 0;
    }

    public void setCoordinates(List<Polygon> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Polygon> getCoordinates() {
        return coordinates;
    }

    public void setArea(double newArea) {
        this.area = newArea;
    }

    public double getArea() {
        return this.area;
    }
    
    public void setBorder(){		// ?
        this.border = 0;
        for (Precinct adjacentPrecinct : adjacentPrecincts) {
            if(adjacentPrecinct.getDistrict().getID() != this.district.getID()){
                this.border = 1;
                break;
            }
        }
    }
    
    public void updateAdjacentBorders(){
        for (Precinct adjacentPrecinct : adjacentPrecincts) {
            adjacentPrecinct.setBorder();
        }
    }
}
