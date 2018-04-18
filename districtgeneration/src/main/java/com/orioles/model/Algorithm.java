package com.orioles.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.orioles.constants.Constants;
import com.orioles.constants.Constraint;
import com.orioles.districtgeneration.AllMeasures;
import javax.persistence.Transient;

public class Algorithm {
	private State state;
	private Map<AllMeasures, Double> measures;
	private List<Constraint> constraints;

	@Transient
	private ArrayList<Move> moves;

	public Algorithm() {
		measures = new HashMap<>();
		state = new State();
		constraints = new ArrayList<>();
		moves = new ArrayList<>();
	}

	public Algorithm(State state, Map<AllMeasures, Double> measures, List<Constraint> constraints) {
		this.state = state;
		this.measures = measures;
		this.constraints = constraints;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Map<AllMeasures, Double> getMeasures() {
		return measures;
	}

	public void setMeasures(Map<AllMeasures, Double> measures) {
		this.measures = measures;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}

	public void removeConstraint(Constraint constraint) {
		constraints.remove(constraint);
	}

	public boolean isImprovement(int goodness, int oldGoodness) {
		return goodness > oldGoodness;
	}

	public ArrayList<Precinct> getCandidates(ArrayList<Precinct> precincts) {
		return null;
	}

	public double calculateImprovement(double goodness, double oldGoodness) {
		return goodness - oldGoodness;
	}

	public List<Precinct> getValidMoves() {
		return null;
	}

	public void startAlgorithm() {
		state.calculateDistrictGoodness(measures);
		for (int iterations = 0; iterations < Constants.MAX_ITERATIONS; iterations++) {
			step();
		}
	}

	private void step() {
		double oldGoodness = state.getGoodness();
		CongressionalDistrict sourceDistrict = state.getStartingDistrict();
		Precinct movingPrecinct = sourceDistrict.getMovingPrecinct();
		List<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();

		for (Precinct adjacentPrecinct : adjacentPrecincts) {
			CongressionalDistrict destDistrict = adjacentPrecinct.getDistrict();
			if (!sourceDistrict.equals(destDistrict)) {
				makeMove(sourceDistrict, destDistrict, movingPrecinct);
				state.calculateDistrictGoodness(measures);
				if (oldGoodness >= state.getGoodness()) {
					makeMove(destDistrict, sourceDistrict, movingPrecinct);
					state.calculateDistrictGoodness(measures);
				} else {
					addMove(sourceDistrict, destDistrict, movingPrecinct);
					break;
				}
			}
		}
	}

	private void addMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
						 Precinct movingPrecinct) {

		Move newMove = new Move(movingPrecinct.getIdentifier(), sourceDistrict.getID(), destDistrict.getID());
		moves.add(newMove);
		movingPrecinct.setDistrict(destDistrict);
	}

	private void makeMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
						  Precinct movingPrecinct) {

		sourceDistrict.removeFromDistrict(movingPrecinct);
		destDistrict.addToDistrict(movingPrecinct);
	}
}
