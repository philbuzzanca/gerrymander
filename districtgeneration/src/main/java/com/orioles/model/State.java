package com.orioles.model;

import com.orioles.constants.Demographic;
import com.orioles.constants.Party;
import com.orioles.constants.Race;
import java.util.*;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;
	private double goodness;
	private boolean hasUpdated;
	private Stats stat;
        
        public State(){
            congressionalDistricts = new ArrayList<>();
            name = "";
            goodness = 0;
            hasUpdated = false;
            stat = new Stats();
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
        Map<Demographic, Long> demographic = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0, demographic);

        congressionalDistricts.stream()
                .map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Stats.summarize(conDistRace, conDistParty, cdStat, stat, demographic));

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

	void setDistrictGoodness(Map<Measure, Double> measures){
		for (CongressionalDistrict cd : congressionalDistricts) {
			List<Double> goodnessVals = new ArrayList<>();
			measures.keySet().forEach(key -> goodnessVals.add(key.calculateGoodness(cd, stat) * measures.get(key)));
			OptionalDouble goodness = goodnessVals.stream().mapToDouble(num -> num).average();
			if (goodness.isPresent())
				cd.setGoodness(goodness.getAsDouble());
		}
	}

    double calculateGoodness() {
		OptionalDouble goodness = congressionalDistricts.stream()
				.mapToDouble(CongressionalDistrict::getGoodness).average();
		if (goodness.isPresent())
			return goodness.getAsDouble();
		else return 0;
	}

    public double getGoodness(){
        return this.goodness;
    }
    
    public void setGoodness(double newGoodness){
        this.goodness = newGoodness;
    }
    
    public CongressionalDistrict getStartingDistrict(){
        return congressionalDistricts.get((int)(Math.random()*congressionalDistricts.size()));
    }
    
    public void setBorderStatus(){
        for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
            ArrayList<Precinct> precincts = (ArrayList)congressionalDistrict.getPrecincts();
            for (Precinct precinct : precincts) {
                precinct.setBorder();
            }
        }
    }
    
    public void setLockedPrecincts(ArrayList<Precinct> lockedPrecincts){
        for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
            ArrayList<Precinct> precincts = (ArrayList)congressionalDistrict.getPrecincts();
            for (Precinct precinct : precincts) {
                if(lockedPrecincts.contains(precinct))
                    precinct.setLocked(true);
            }
        }
    }
    
    public void setLockedDistricts(ArrayList<CongressionalDistrict> lockedDistricts){
            for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
                if(lockedDistricts.contains(congressionalDistrict)){
                    ArrayList<Precinct> precincts = (ArrayList)congressionalDistrict.getPrecincts();
                    for (Precinct precinct : precincts) {
                        precinct.setLocked(true);
                    }
                }                  
            }
    }
}
