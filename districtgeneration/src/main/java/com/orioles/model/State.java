package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.constants.Race;

import java.util.*;

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
                .forEach(cdStat -> Stats.summarize(conDistRace, conDistParty, cdStat, stat));

        hasUpdated = true;
		return stat;
    }
    
    public CongressionalDistrict getDistrictByID (int districtID){
        return congressionalDistricts.stream()
                .filter(district -> districtID == district.getID())
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

	void setGoodness(Map<Measure, Double> measures){
		for (CongressionalDistrict cd : congressionalDistricts) {
			List<Double> goodnessVals = new ArrayList<>();
			measures.keySet().forEach(key -> goodnessVals.add(key.calculateGoodness(cd) * measures.get(key)));
			OptionalDouble goodness = goodnessVals.stream().mapToDouble(num -> num).average();
			if (goodness.isPresent())
				cd.setGoodness(goodness.getAsDouble());
		}
	}

    public double getGoodness(){
		OptionalDouble goodness = congressionalDistricts.stream()
						.mapToDouble(CongressionalDistrict::getGoodness).average();
		if (goodness.isPresent())
			return goodness.getAsDouble();
		else return 0;
    }
}
