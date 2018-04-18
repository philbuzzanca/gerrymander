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
    private ArrayList<Move> moves;
    private int iterations;
    private boolean paused;

    public Algorithm(){
        measures = new HashMap<Measure, Double>();
        state = new State();
        iterations = 0;
        constraints = new ArrayList<Constraint>();
        paused = false;
        moves = new ArrayList<Move>();
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
    
    public Precinct getMoves(){
        return null;
    }
    
    public void setMoves(ArrayList<Move> moves){
        this.moves = moves;
    }
    
    public boolean getPaused(){
        return this.paused;
    }
    
    public void setPaused(boolean pause){
        this.paused = pause;
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
    
    public ArrayList<Precinct> getCandidates(ArrayList<Precinct> precincts){
        return null;
    }
    
    public double calculateImprovement(double goodness, double oldGoodness){
        return goodness - oldGoodness;
    }

    public boolean checkIterations(){
        return false;
    }
    
    public void startAlgorithm(){
        state.calculateGoodness(measures);
        while(this.iterations<Constants.MAXITERATIONS){
            step();
        }
    }

    public void step(){
        double oldGoodness = state.getGoodness();
        CongressionalDistrict sourceDistrict = state.getStartingDistrict();
        Precinct movingPrecinct = sourceDistrict.getMovingPrecinct();
        ArrayList<Precinct> adjacentPrecincts = movingPrecinct.getAdjacentPrecincts();
        
        for(Precinct adjacentPrecinct : adjacentPrecincts) {
            CongressionalDistrict destDistrict = adjacentPrecinct.getDistrict();
            if(sourceDistrict.getID() != destDistrict.getID()){
                makeMove(sourceDistrict, destDistrict, movingPrecinct);
                state.calculateGoodness(measures);
                double newGoodness = state.getGoodness();
                if(oldGoodness>=newGoodness){
                    makeMove(destDistrict, sourceDistrict, movingPrecinct);
                    state.calculateGoodness(measures);
                }
                else{
                    addMove(sourceDistrict, destDistrict, movingPrecinct);
                    break;
                }
            }
        }
    }
    
    public void addMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
            Precinct movingPrecinct){
        
        Move newMove = new Move(movingPrecinct.getIdentifier(), sourceDistrict.getID(),
            destDistrict.getID());
        moves.add(newMove);
        movingPrecinct.setDistrict(destDistrict);
    }
    
    public void makeMove(CongressionalDistrict sourceDistrict, CongressionalDistrict destDistrict,
            Precinct movingPrecinct){
        
        sourceDistrict.removeFromDistrict(movingPrecinct);
        destDistrict.addToDistrict(movingPrecinct);
        
    }
}
