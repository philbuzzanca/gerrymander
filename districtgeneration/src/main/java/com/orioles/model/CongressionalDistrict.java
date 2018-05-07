package com.orioles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;
import com.orioles.helper_model.Polygon;

import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

public class CongressionalDistrict implements Cloneable {
	private int ID;
	private List<Precinct> precincts;
	@JsonIgnore
	private List<Precinct> pBorders;
	@JsonIgnore
	private double goodness;
	@JsonIgnore
	private boolean isDirty;
	@JsonIgnore
	private Stats stat;
	@JsonIgnore
	private double area;

	public CongressionalDistrict() {}

	public CongressionalDistrict(int ID) {
		this.ID = ID;
		this.goodness = -1;
		this.area = 0;
		this.isDirty = true;
	}

	public CongressionalDistrict(List<Precinct> ps, int ID) {
		this(ID);
		this.precincts = ps;
	}

	public List<Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(List<Precinct> precincts) {
		this.precincts = precincts;
	}

	public int getNumPrecincts() {
		return this.precincts.size();
	}

	public List<Precinct> getpBorders() {
		return pBorders;
	}

	public void setpBorders(List<Precinct> pBorders) {
		this.pBorders = pBorders;
	}

	int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	double getGoodness() {
		return goodness;
	}

	void setGoodness(double goodness) {
		this.goodness = goodness;
	}

	public Stats summarize() {
		if (!isDirty)
			return stat;

		stat = new Stats();
		precincts.stream().map(Precinct::getStats).forEach(precinctStat -> Stats.summarize(precinctStat, stat));
		isDirty = false;
		return stat;
	}

	void removeFromDistrict(Precinct precinct) {
		isDirty = true;
		precincts.remove(precinct);
	}

	void addToDistrict(Precinct precinct) {
		isDirty = true;
		precincts.add(precinct);
	}

	public Precinct getPrecinctById(int precinctId) {
		return precincts.stream().filter(precinct -> precinct.getIdentifier() == precinctId)
				.findAny().orElse(null);
	}

	private Precinct getRandomPrecinct() {
		return precincts.get((int) (Math.random() * precincts.size()));
	}

	public double getArea() {
		if (!isDirty)
			return this.area;
		isDirty = false;
		return this.area = precincts.stream().mapToDouble(Precinct::getArea).sum();
	}

	Precinct getMovingPrecinct() {
		Precinct movingPrecinct;
		boolean isBorderPrecinct = false;
		boolean locked = false;
		do {
			movingPrecinct = getRandomPrecinct();
			List<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();
			for (Precinct adjacentPrecinct : adjacentPrecincts) {
				if (movingPrecinct.getDistrict().getID() != adjacentPrecinct.getDistrict().getID()) {
					isBorderPrecinct = true;
				}
			}

			// ?????????????????? TBD
//			isBorderPrecinct = movingPrecinct.setBorder();		// ?
			locked = movingPrecinct.isLocked();
		} while (isBorderPrecinct || locked);		// FIXME: end condition
		return movingPrecinct;
	}

	@JsonIgnore
	public double getPerimeter() {
		Set<Edge> allEdges = new HashSet<>();
		Set<Edge> repeatedEdges = new HashSet<>();
		for (Precinct currentPrecinct : precincts) {
			List<Edge> edges = currentPrecinct.getCoordinates().stream()
					.map(Polygon::getAllEdges).flatMap(Collection::stream).collect(Collectors.toList());
			allEdges.addAll(edges.stream().distinct().collect(Collectors.toList()));

			Set<Edge> uniques = new HashSet<>();
			repeatedEdges.addAll(edges.stream().filter(e -> !uniques.add(e)).collect(Collectors.toList()));
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
