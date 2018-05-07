package com.orioles.model;

import java.util.ArrayList;
import java.util.HashMap;
import com.orioles.constants.Constants;
import com.orioles.constants.Constraint;
import java.util.List;
import java.util.Map;


public class Algorithm {
	private State state;
	private Map<Measure, Double> measures;
	private List<Constraint> constraints;
	private ArrayList<Move> allMoves;
        private ArrayList<Move> movesSinceUpdate;
	private int iterations;
	private boolean paused;

	public Algorithm() {
		measures = new HashMap<>();
		state = new State();
		iterations = 0;
		constraints = new ArrayList<>();
		paused = false;
		allMoves = new ArrayList<>();           //will be used to save the redistricting to a user
                movesSinceUpdate = new ArrayList<>();   //will be used to update the map while the algorithm is running, gets cleared after updating
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Map<Measure, Double> getMeasures() {
		return measures;
	}

	public void setMeasures(Map<Measure, Double> measures) {
		this.measures = measures;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public ArrayList<Move> getAllMoves() {
		return this.allMoves;
	}

	public void setAllMoves(ArrayList<Move> moves) {
		this.allMoves = moves;
	}
        
        public ArrayList<Move> getMovesSinceUpdate(){
                return this.movesSinceUpdate;
        }
        
        public void setMovesSinceUpdate(ArrayList<Move> moves){
            this.movesSinceUpdate = moves;
        }
        
        public void clearMoves(){
            this.movesSinceUpdate.clear();
        }

	public boolean getPaused() {
		return this.paused;
	}

	public void setPaused(boolean pause) {
		this.paused = pause;
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

	public boolean checkIterations() {
		return false;
	}

	public void startAlgorithm() {
		state.setDistrictGoodness(measures);
                state.setBorderStatus();
		while (this.iterations < Constants.MAX_ITERATIONS) {
			step();
		}
	}

	public void step() {
		double oldGoodness = state.getGoodness();
		CongressionalDistrict sourceDistrict = state.getStartingDistrict();
		Precinct movingPrecinct = sourceDistrict.getMovingPrecinct();
		ArrayList<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();

		for (Precinct adjacentPrecinct : adjacentPrecincts) {
			CongressionalDistrict destDistrict = adjacentPrecinct.getDistrict();
			if (sourceDistrict.getID() != destDistrict.getID()) {
				makeMove(sourceDistrict, destDistrict, movingPrecinct);
				state.setDistrictGoodness(measures);
				double newGoodness = state.getGoodness();
				if (oldGoodness >= newGoodness) {
					makeMove(destDistrict, sourceDistrict, movingPrecinct);
					state.setDistrictGoodness(measures);
				} else {
                                        movingPrecinct.setBorder();
                                        movingPrecinct.updateAdjacentBorders();
					addMove(sourceDistrict, destDistrict, movingPrecinct);
					break;
				}
			}
		}
	}

	public void addMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
						Precinct movingPrecinct) {

		Move newMove = new Move(movingPrecinct.getIdentifier(), sourceDistrict.getID(),
				destDistrict.getID());
		allMoves.add(newMove);
                movesSinceUpdate.add(newMove);
		movingPrecinct.setDistrict(destDistrict);
	}

	public void makeMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
						 Precinct movingPrecinct) {

		sourceDistrict.removeFromDistrict(movingPrecinct);
		destDistrict.addToDistrict(movingPrecinct);
	}
        
        public void makeSpecifiedMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
						Precinct movingPrecinct) {
            
            Move newMove = new Move(movingPrecinct.getIdentifier(), sourceDistrict.getID(),
				destDistrict.getID());
            allMoves.add(newMove);
            movesSinceUpdate.add(newMove);
            sourceDistrict.removeFromDistrict(movingPrecinct);
            destDistrict.addToDistrict(movingPrecinct);
            movingPrecinct.setDistrict(destDistrict);
            movingPrecinct.setLocked(true);
            
        }
        
        public void loadOldRedistricting(ArrayList<Move> moves){
            for(Move move: moves){
                int sourceDistrictID = move.getSourceDistrict();
                int destinationDistrictID = move.getDestDistrict();
                int precinctID = move.getPrecinct();
                CongressionalDistrict sourceDistrict = new CongressionalDistrict();
                CongressionalDistrict destinationDistrict = new CongressionalDistrict();
                Precinct movingPrecinct = new Precinct();
        
                for(CongressionalDistrict district: state.getCongressionalDistricts()){
                    if(district.getID()==sourceDistrictID){
                        sourceDistrict = district;
                        for(Precinct precinct: sourceDistrict.getPrecincts()){
                            if(precinct.getIdentifier()==precinctID){
                                movingPrecinct = precinct;
                                break;
                            }
                        }
                        break;
                    }
                }
                for(CongressionalDistrict district: state.getCongressionalDistricts()){
                    if(district.getID()==destinationDistrictID){
                        sourceDistrict = district;
                        break;
                    }
                }
                makeMove(sourceDistrict, destinationDistrict, movingPrecinct);
            }
        }
}
