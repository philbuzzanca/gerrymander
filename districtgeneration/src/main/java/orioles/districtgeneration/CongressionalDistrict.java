package orioles.districtgeneration;

import java.util.List;

class CongressionalDistrict implements Cloneable {
    private List<Precinct> precincts;
    private String name;
    private double oldGoodness;
    
    public Stats summarize(){
        return null;
    }
    
    public Precinct choosePrecinct(){
        return null;
    }
    
    public Precinct getStartingPrecinct(){
        return null;
    }
    
    public Precinct removeFromDistrict(Precinct precinct){
        return precinct;
    }
    
    public void addToDistrict(Precinct precinct){
    }
    
    public Precinct getPrecinct(int precinctId){
        return null;
    }

    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOldGoodness() {
        return oldGoodness;
    }

    public void setOldGoodness(double oldGoodness) {
        this.oldGoodness = oldGoodness;
    }
    
    
}
