package com.orioles.model;


import com.orioles.constants.Party;
import com.orioles.constants.Race;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;

	private boolean hasUpdated;
	private Stats stat;

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

	public int getNumPrecincts() {
		return congressionalDistricts.size();
	}

	public void addDistrict(CongressionalDistrict cd) {
		congressionalDistricts.add(cd);
	}
    
    public Stats summarize() {
		if (!hasUpdated)
			return stat;

        Map<Race, Long> conDistRace = new HashMap<>();
        Map<Party, Long> conDistParty = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0);

        congressionalDistricts.stream()
                .map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Summary.summarize(conDistRace, conDistParty, cdStat, stat));

        hasUpdated = true;
		return stat;
    }
    
    public CongressionalDistrict getDistrictByName (String districtName){
        return congressionalDistricts.stream()
				.filter(precinct -> districtName.equals(precinct.getName()))
				.findFirst().orElse(null);
    }

//    public CongressionalDistrict getDistrictByNumber(int districtNumber){
//		return congressionalDistricts.stream()
//				.filter(precinct -> precinct.getIdentifier() == districtNumber)
//				.findFirst().orElse(null);
//    }
    
    public List<CongressionalDistrict> getGerrymanderedDistricts(){
        return null;
    }

	public void setStartingGoodness(Map<Measure, Double> measures){
		for(int i=0; i<congressionalDistricts.size(); i++){

			ArrayList<Double> goodnessVals = new ArrayList<Double>();
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
}