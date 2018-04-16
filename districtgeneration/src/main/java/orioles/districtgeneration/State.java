package orioles.districtgeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;
    private String originalState;
    
    public State(){
        congressionalDistricts = new ArrayList<>();
        name = "";
        originalState = "";
    }
    
    public Stats summarize(){
        return null;
    }
    
    public Stats getCongressionalStats(String district){
        return null;
    }
    
    public CongressionalDistrict getDistrict(String districtName){
        for (CongressionalDistrict district : congressionalDistricts) {
            if(district.getName().equals(districtName)){
                return district;
            }
        }
        return null;
    }
    
    public CongressionalDistrict getDistrict(int districtNumber){
        return null;
    }
    
    public List<CongressionalDistrict> getGerrymanderedDistricts(){
        return null;
    }

    public List<CongressionalDistrict> getCongressionalDistricts() {
        return congressionalDistricts;
    }

    public void setCongressionalDistricts(List<CongressionalDistrict> congressionalDistricts) {
        this.congressionalDistricts = congressionalDistricts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalState() {
        return originalState;
    }

    public void setOriginalState(String originalState) {
        this.originalState = originalState;
    }
   
    public int getNumPrecincts(){
    	return 0;
    }
    
    public CongressionalDistrict getStartingDistrict(){
    	return null;
    }
    
    public void setStartingGoodness(Map<Measure, Double> measures){
        for(int i=0; i<congressionalDistricts.size(); i++){
            
            ArrayList<Double> goodnessVals = new ArrayList<>();
            for ( Measure key : measures.keySet()) {
                goodnessVals.add(key.calculateGoodness(congressionalDistricts.get(i))*measures.get(key));
            }
            
            double goodness = 0;
            for(int j=0; j<goodnessVals.size();j++){
                goodness+=goodnessVals.get(j);
            }
            goodness = goodness/goodnessVals.size();
            congressionalDistricts.get(i).setOldGoodness(goodness);
        }
    }
    
    public void addDistrict(CongressionalDistrict district){
        congressionalDistricts.add(district);
    }
    
    
    
}
