package orioles.model;

import java.util.List;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;
    private String originalState;
    
    public Stats summarize(){
        return null;
    }
    
    public Stats getCongressionalStats(String district){
        return null;
    }
    
    public CongressionalDistrict getDistrict(String districtName){
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
    
    
}
