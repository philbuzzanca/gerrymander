package orioles.model;

import java.util.ArrayList;
import java.util.HashMap;
import orioles.constants.Constraint;

import java.util.List;
import java.util.Map;
import orioles.constants.Constants;

public class Algorithm {
    private State state;
    private Map<Measure, Double> measures;
    private List<Constraint> constraints;
    private int iterations;
    private boolean paused;

    public Algorithm(){
        measures = new HashMap<Measure, Double>();
        state = new State();
        iterations = 0;
        constraints = new ArrayList<Constraint>();
        paused = false;
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
    
    public Precinct getMove(){
        return null;
    }
    
    public void startAlgorithm(){
        state.setGoodness(measures);
        while(this.iterations<Constants.MAXITERATIONS && paused==false){
            step();
        }
    }
    
    public void addConstraint(Constraint constraint){
        constraints.add(constraint);
    }
    
    public void removeConstraint(Constraint constraint){
        constraints.remove(constraint);
    }
    
    public boolean isImprovement(int goodness, int oldGoodness){
        return goodness > oldGoodness;
    }
    
    public List<Precinct> getCandidates(List<Precinct> precincts){
        return null;
    }
    
    public double calculateImprovement(double goodness, double oldGoodness){
        return goodness - oldGoodness;
    }

    public boolean checkIterations(){
        return false;
    }
    
    public CongressionalDistrict getStartingDistrict(){
        return null;
    }

    public void step(){
        double oldGoodness = state.getGoodness();
        CongressionalDistrict fromDistrict = getStartingDistrict();
        Precinct movingPrecinct = fromDistrict.getRandomPrecinct();
        ArrayList<Precinct> adjacentPrecincts = (ArrayList)movingPrecinct.getAdjacentPrecincts();
        for(Precinct adjacentPrecinct : adjacentPrecincts) {
            CongressionalDistrict toDistrict = adjacentPrecinct.getDistrict();
            if(fromDistrict.getID() != toDistrict.getID()){
                fromDistrict.removeFromDistrict(movingPrecinct);
                toDistrict.addToDistrict(movingPrecinct);
                state.setGoodness(measures);
                double newGoodness = state.getGoodness();
                if(oldGoodness>=newGoodness){
                  toDistrict.removeFromDistrict(movingPrecinct);
                  fromDistrict.addToDistrict(movingPrecinct);
                  state.setGoodness(measures);
                  //            if move is improvement then we need to change CD in precinct,
                  //            also need to change border precicnt in all adjacent precincts to represent the moving
                  //            precincts new CD
                }
                else{
                    //add a move to the changes list
                }
            }
        }
    }
}
