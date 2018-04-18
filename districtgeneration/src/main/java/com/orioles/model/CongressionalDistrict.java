package com.orioles.model;


import com.orioles.constants.Party;
import com.orioles.constants.Race;
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

	public CongressionalDistrict() {}

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

	public double calculatePerimeter() {
		double perimeter = 0;
		ArrayList<Edge> edges1 = new ArrayList<>();
		ArrayList<Edge> edges2 = new ArrayList<>();
		for (Precinct currentPrecinct : precincts) {
			List<Coordinate> coordinates = currentPrecinct.getCoordinates();
			for (int j = 0; j < coordinates.size() - 1; j++) {
				Coordinate p1 = coordinates.get(j);
				Coordinate p2 = coordinates.get(j + 1);
				Edge edge = new Edge(p1, p2);
				Boolean found = false;
				for (Edge checkedge : edges1) {
					if (checkedge.equals(edge))
						found = true;
				}
				if (found) {
					System.out.println("here");
					Boolean found2 = false;
					for (Edge checkedge : edges2) {
						if (checkedge.equals(edge))
							found2 = true;
					}
					if (found2)
						edges2.add(edge);
				} else {
					edges1.add(edge);
				}
			}
		}
		for (Edge edge : edges2) {
			edges1.remove(edge);
		}
		for (Edge edge : edges1) {
			Coordinate point1 = edge.getP1();
			Coordinate point2 = edge.getP2();
			double distance = calculateDistance(point1, point2);
			perimeter += distance;
		}

		return perimeter;
	}

	public double calculateDistance(Coordinate point1, Coordinate point2) {
		//calculates distance useing haversine formula
		double radius = 6371.01;

		Coordinate p1 = new Coordinate(-77.860192445970085, 39.153000129599988);
		Coordinate p2 = new Coordinate(-77.862840901004034, 39.145148506742501);

		double latDistance = Math.toRadians(p2.getY() - p1.getY());
		double lonDistance = Math.toRadians(p2.getX() - p1.getX());

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(p1.getY())) * Math.cos(Math.toRadians(p2.getY()))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = radius * c;

		distance = Math.pow(distance, 2);
		return Math.sqrt(distance);
	}
}
