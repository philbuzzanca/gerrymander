package com.orioles.model;

import com.orioles.constants.Constants;
import com.orioles.constants.Party;
import com.orioles.constants.Race;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CongressionalDistrict implements Cloneable {
	private int ID;
	private List<Precinct> precincts;
	private double goodness;
	private boolean hasUpdated;
	private Stats stat;

	public CongressionalDistrict() {
	}

	public CongressionalDistrict(List<Precinct> precincts, int ID) {
		this.ID = ID;
		this.precincts = precincts;
		this.goodness = -1;
		this.hasUpdated = false;
	}

	public List<Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(List<Precinct> precincts) {
		this.precincts = precincts;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public double getGoodness() {
		return goodness;
	}

	public void setGoodness(double goodness) {
		this.goodness = goodness;
	}

	public double getArea() {
		return 0;
	}

	public double getPerimeter() {
		return 0;
	}

	public Stats summarize() {
		if (!hasUpdated)
			return stat;

		Map<Race, Long> conDistRace = new HashMap<>();
		Map<Party, Long> conDistParty = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0);

		precincts.stream().map(Precinct::getStats)
				.forEach(precinctStat -> Stats.summarize(conDistRace, conDistParty, precinctStat, stat));

		hasUpdated = true;
		return stat;
	}

	public Precinct getStartingPrecinct() {
		return null;
	}

	public Precinct choosePrecinct() {
		return null;
	}

	public void removeFromDistrict(Precinct precinct) {
		hasUpdated = false;
		precincts.remove(precinct);
	}

	public void addToDistrict(Precinct precinct) {
		hasUpdated = false;
		precincts.add(precinct);
	}

	public Precinct getPrecinctById(int precinctId) {
		return precincts.stream()
				.filter(precinct -> precinct.getIdentifier() == precinctId)
				.findFirst().orElse(null);
	}

	public Precinct getRandomPrecinct() {
		return precincts.get((int) (Math.random() * precincts.size()));
	}

	public double calculateArea() {
		return 100;
	}

	public Precinct getMovingPrecinct() {
		Precinct movingPrecinct;
		boolean isBorderPrecinct = false;
		do {
			movingPrecinct = getRandomPrecinct();
			List<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();
			for (Precinct adjacentPrecinct : adjacentPrecincts) {
				if (movingPrecinct.getDistrict().getID() != adjacentPrecinct.getDistrict().getID())
					isBorderPrecinct = true;
			}
		} while (isBorderPrecinct);
		return movingPrecinct;
	}

	public double calculatePerimeter() {
		List<Edge> allEdges = new ArrayList<>();
		List<Edge> repeatedEdges = new ArrayList<>();
		for (Precinct currentPrecinct : precincts) {
			List<Coordinate> coordinates = currentPrecinct.getCoordinates();
			for (int j = 0; j < coordinates.size() - 1; j++) {
				Edge edge = new Edge(coordinates.get(j), coordinates.get(j + 1));
				if (allEdges.contains(edge)) {
					if (repeatedEdges.contains(edge))
						repeatedEdges.add(edge);
				} else {
					allEdges.add(edge);
				}
			}
		}
		allEdges.removeAll(repeatedEdges);
		return allEdges.stream().mapToDouble(this::calculateDistance).sum();
	}

	private double calculateDistance(Edge edge) {
		Coordinate point1 = edge.getP1();
		Coordinate point2 = edge.getP2();
		//calculates distance using haversine formula
		double radius = Constants.EARTH_RADIUS;
		double latDistance = Math.toRadians(point2.getY() - point1.getY());
		double lonDistance = Math.toRadians(point2.getX() - point1.getX());

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(point1.getY())) * Math.cos(Math.toRadians(point2.getY()))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		return radius * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}
}
