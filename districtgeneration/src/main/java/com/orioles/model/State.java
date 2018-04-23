package com.orioles.model;

import com.orioles.constants.Constants;
import com.orioles.districtgeneration.AllMeasures;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;

    @Transient
	private boolean hasUpdated;

    @Transient
	private Stats stat;

	public State(){
		congressionalDistricts = new ArrayList<>();
		name = "";
		hasUpdated = false;
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

		stat = new Stats();
        congressionalDistricts.stream()
                .map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Stats.summarize(cdStat, stat));
		hasUpdated = true;
		return stat;
	}

    public CongressionalDistrict getDistrictByID (int districtID){
        return congressionalDistricts.stream()
                .filter(district -> districtID == district.getID())
                .findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public List<Pair<Integer, Double>> getGerrymanderedDistricts(){
		return congressionalDistricts.stream()
				.filter(district -> district.getGoodness() < Constants.GERRYMANDERING_THRESHOLD)
				.sorted(Comparator.comparingDouble(CongressionalDistrict::getGoodness))
				.map(cd -> new Pair<>(cd.getID(), cd.getGoodness()))
				.collect(Collectors.toList());
    }

	void calculateDistrictGoodness(Map<AllMeasures, Integer> measures){
		for (CongressionalDistrict cd : congressionalDistricts) {
			List<Double> goodnessVals = new ArrayList<>();
			measures.keySet().forEach(key -> goodnessVals.add(key.calculateGoodness(cd) * measures.get(key)));
			OptionalDouble goodness = goodnessVals.stream().mapToDouble(num -> num).average();
			cd.setGoodness(goodness.isPresent() ? goodness.getAsDouble() : -1);
		}
	}

    double getGoodness() {
		OptionalDouble goodness = congressionalDistricts.stream()
				.mapToDouble(CongressionalDistrict::getGoodness).average();
		return goodness.isPresent() ? goodness.getAsDouble() : 0;
	}

	CongressionalDistrict getStartingDistrict(){
        return getRandomDistrict();
    }
    
    private CongressionalDistrict getRandomDistrict() {
        return congressionalDistricts.get((int)(Math.random()*congressionalDistricts.size()));
    }
}
