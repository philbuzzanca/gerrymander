package orioles.districtgeneration;

import java.util.List;
import java.util.Map;

public class Algorithm {
    private State state;
    private Map<Measure, Double> measures;
    private List<Constraint> constraints;
    private int iterations;
    
    public Precinct getMove(){
        return null;
    }
    
    public void startAlgorithm(){
        state.setStartingGoodness(measures);
    	int maxIterations = state.getNumPrecincts();
    	for(;iterations<maxIterations; iterations++){
    		step();
    	}
    	
    }
    
    public void addConstraint(Constraint constraint){
        constraints.add(constraint);
    }
    
    public void removeConstraint(Constraint constraint){
        constraints.remove(constraint);
    }
    
    public Map<Measure, Double> normalize(Map<Measure, Double> measures){
        return null;
    }
    
    public boolean isImprovement(int goodness, int oldGoodness){
        return false;
    }
    
    public List<Precinct> getCandidates(List<Precinct> precincts){
        return null;
    }
    
    public double calculateImprovement(double goodness, double oldGoodness){
        return 0;
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
    
    public boolean checkIterations(){
        return false;
    }
    
    public CongressionalDistrict getStartingDistrict(){
        return null;
    }
    
    public void step(){	
    	
    }
    
    public void movePrecinct(Precinct precinct){
    }
    
    
}
