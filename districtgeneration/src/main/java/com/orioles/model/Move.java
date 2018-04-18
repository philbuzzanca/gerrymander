package com.orioles.model;


public class Move {
	private int precinctID;
	private int sourceDistrictID;
	private int destinationDistrictID;

	public Move() {
		precinctID = 0;
		sourceDistrictID = 0;
		destinationDistrictID = 0;
	}

	public Move(int precinct, int source, int destination) {
		precinctID = precinct;
		sourceDistrictID = source;
		destinationDistrictID = destination;
	}

	public int getPrecinct() {
		return this.precinctID;
	}

	public int getSourceDistrict() {
		return this.sourceDistrictID;
	}

	public int getDestDistrict() {
		return this.destinationDistrictID;
	}

	public void setPrecinctID(int precinct) {
		this.precinctID = precinct;
	}

	public void setSourceDistrict(int district) {
		this.sourceDistrictID = district;
	}

	public void setDestDistrict(int district) {
		this.destinationDistrictID = district;
	}
}
