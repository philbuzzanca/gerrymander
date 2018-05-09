package com.orioles.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import com.orioles.constants.Constants;
import com.orioles.districtgeneration.Constraint;
import com.orioles.districtgeneration.AllMeasures;
import javax.persistence.Transient;

public class Algorithm implements Serializable {
	private State state;
	private Map<AllMeasures, Integer> measures;
	private Map<Constraint, Boolean> constraints;

	@Transient
	private List<Move> masterMoves;

	@Transient
	private List<Move> currMoves;

	public Algorithm() {
		measures = new HashMap<>();
		state = new State();
		constraints = new HashMap<>();
		masterMoves = new ArrayList<>();
	}

	public Algorithm(State state, Map<AllMeasures, Integer> measures, Map<Constraint, Boolean> constraints) {
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

	public Map<AllMeasures, Integer> getMeasures() {
		return measures;
	}

	public void setMeasures(Map<AllMeasures, Integer> measures) {
		this.measures = measures;
	}

	public Map<Constraint, Boolean> getConstraints() {
		return constraints;
	}

	public void setConstraints(Map<Constraint, Boolean> constraints) {
		this.constraints = constraints;
	}

	public List<Move> getMasterMoves() {
		return masterMoves;
	}

	public void setMasterMoves(ArrayList<Move> masterMoves) {
		this.masterMoves = masterMoves;
	}

	public List<Move> getCurrMoves() {
		return currMoves;
	}

	public void setCurrMoves(ArrayList<Move> currMoves) {
		this.currMoves = currMoves;
	}
        
	public void clearCurrMoves(){
		this.currMoves.clear();
	}

	public void addConstraint(Constraint constraint) {
		constraints.put(constraint, true);
	}

	public void removeConstraint(Constraint constraint) {
		constraints.remove(constraint);
	}

	public List<Precinct> getCandidates(List<Precinct> precincts) {
		return null;
	}

	public List<Precinct> getValidMoves() {
		return null;
	}

	public void setup() {
		state.calculateDistrictGoodness(measures);
		state.setBorderStatus();
	}

	public void runAlgorithm() {
		currMoves = new ArrayList<>();
		IntStream.range(0, Constants.MAX_ITERATIONS).forEach(iteration -> step());
		masterMoves.addAll(currMoves);
	}

	private void step() {
		double oldGoodness = state.getGoodness();
		CongressionalDistrict sourceDistrict = state.getStartingDistrict();
		Precinct movingPrecinct = sourceDistrict.getMovingPrecinct();

		for (Precinct adjacentPrecinct : movingPrecinct.getAdjacentPrecincts()) {
			CongressionalDistrict destDistrict = adjacentPrecinct.getDistrict();
			if (!sourceDistrict.equals(destDistrict)) {
				makeMove(sourceDistrict, destDistrict, movingPrecinct);
				state.calculateDistrictGoodness(measures);
				if (oldGoodness >= state.getGoodness()) {
					makeMove(destDistrict, sourceDistrict, movingPrecinct);
					state.calculateDistrictGoodness(measures);
				} else {
					movingPrecinct.setBorder();
					movingPrecinct.updateAdjacentBorders();
					addMove(sourceDistrict, destDistrict, movingPrecinct);
					break;
				}
			}
		}
	}

	// FIXME: Weird Set of three below.
	private void addMove(CongressionalDistrict srcDist, CongressionalDistrict destDist, Precinct movingPrecinct) {
		currMoves.add(new Move(movingPrecinct.getIdentifier(), srcDist.getID(), destDist.getID()));
		movingPrecinct.setDistrict(destDist);
	}

	private void makeMove(CongressionalDistrict srcDist, CongressionalDistrict destDist, Precinct movingPrecinct) {
		srcDist.removeFromDistrict(movingPrecinct);
		destDist.addToDistrict(movingPrecinct);
	}
        
	public void makeManualMove(CongressionalDistrict srcDist, CongressionalDistrict destDist, Precinct movingPrecinct) {
		currMoves.add(new Move(movingPrecinct.getIdentifier(), srcDist.getID(), destDist.getID()));
		makeMove(srcDist, destDist, movingPrecinct);
		movingPrecinct.setLocked(true);
	}

	public void loadOldRedistricting(State newState, List<Move> moves){
		this.state = newState;
		moves.forEach(move -> {
			CongressionalDistrict srcDest = newState.getDistrictByID(move.getSourceDistrict());
			makeMove(srcDest, newState.getDistrictByID(move.getDestDistrict()),
					srcDest.getPrecinctById(move.getPrecinct()));
		});
	}
}
