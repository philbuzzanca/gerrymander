package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.constants.Race;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;

import java.util.*;

public class CongressionalDistrict implements Cloneable {
	private int ID;
	private List<Precinct> precincts;
	private double goodness;
	private boolean isDirty;
	private Stats stat;
	private double area;

	public CongressionalDistrict() {
	}

	public CongressionalDistrict(List<Precinct> precincts, int ID) {
		this.ID = ID;
		this.precincts = precincts;
		this.goodness = -1;
		this.area = 0;
		this.isDirty = false;
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

	public Stats summarize() {
		if (!isDirty)
			return stat;

		Map<Race, Long> conDistRace = new HashMap<>();
		Map<Party, Long> conDistParty = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0);

		precincts.stream().map(Precinct::getStats)
				.forEach(precinctStat -> Stats.summarize(conDistRace, conDistParty, precinctStat, stat));

		isDirty = false;
		return stat;
	}

	public Precinct getStartingPrecinct() {
		return null;
	}

	public Precinct choosePrecinct() {
		return null;
	}

	public void removeFromDistrict(Precinct precinct) {
		isDirty = true;
		precincts.remove(precinct);
	}

	public void addToDistrict(Precinct precinct) {
		isDirty = true;
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

	public double getArea() {
		return this.area = precincts.stream().mapToDouble(Precinct::getArea).sum();
	}

	public Precinct getMovingPrecinct() {
		Precinct movingPrecinct;
		boolean isBorderPrecinct = false;
		do {
			movingPrecinct = getRandomPrecinct();
			List<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();
			for (Precinct adjacentPrecinct : adjacentPrecincts) {
				if (movingPrecinct.getDistrict().getID() != adjacentPrecinct.getDistrict().getID()) {
					isBorderPrecinct = true;
				}
			}
		} while (isBorderPrecinct);
		return movingPrecinct;
	}

	public double getPerimeter() {
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
		return allEdges.stream().mapToDouble(Edge::calculateDistance).sum();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CongressionalDistrict otherDistrict = (CongressionalDistrict) o;
		return this.ID == otherDistrict.ID ;
	}
}
