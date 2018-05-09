package com.orioles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orioles.constants.Constants;
import com.orioles.districtgeneration.AllMeasures;
import com.orioles.helper_model.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class State implements Cloneable, Serializable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;
    private boolean hasUpdated;
    private Stats stat;
    private double goodness;

    public State() {
        congressionalDistricts = new ArrayList<>();
        name = "";
        goodness = 0;
        hasUpdated = true;
        stat = new Stats();
    }

    public State(List<CongressionalDistrict> cdList, String name) {
        this();
        this.congressionalDistricts = cdList;
        this.name = name;
    }

    public List<CongressionalDistrict> getCongressionalDistricts() {
        return congressionalDistricts;
    }

    public void setCongressionalDistricts(List<CongressionalDistrict> cds) {
        this.congressionalDistricts = cds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public int getNumPrecincts() {
        return congressionalDistricts.stream().mapToInt(CongressionalDistrict::getNumPrecincts).sum();
    }

    @JsonIgnore
    public int getNumDistricts() {
        return congressionalDistricts.size();
    }

    public void addDistrict(CongressionalDistrict cd) {
        congressionalDistricts.add(cd);
    }

    public void setGoodness(double newGoodness) {
        this.goodness = newGoodness;
    }

    public List<Precinct> getAllPrecincts() {
        return congressionalDistricts.stream().map(CongressionalDistrict::getPrecincts)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Stats summarize() {
        if (!hasUpdated) {
            return stat;
        }

        stat = new Stats();
        congressionalDistricts.stream().map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Stats.summarize(cdStat, stat));
        hasUpdated = true;
        return stat;
    }

    public CongressionalDistrict getDistrictByID(int districtID){
        return congressionalDistricts.stream()
                .filter(district -> districtID == district.getID()).findFirst().orElse(null);
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public Map<Integer, Double> getGerrymanderedDistricts() {
    	Map<Integer, Double> gerry = new HashMap<>();
        congressionalDistricts.stream()
                .filter(district -> district.getGoodness() < Constants.GERRYMANDERING_THRESHOLD)
                .sorted(Comparator.comparingDouble(CongressionalDistrict::getGoodness))
				.forEach(cd -> gerry.put(cd.getID(), cd.getGoodness()));
        return gerry;
    }

    void calculateDistrictGoodness(Map<AllMeasures, Integer> measures) {
        for (CongressionalDistrict cd : congressionalDistricts) {
            double goodness = measures.keySet().stream()
                    .mapToDouble(key -> key.calculateGoodness(cd, this) * measures.get(key)).sum();
            cd.setGoodness(goodness / measures.values().stream().filter(v -> v!= 0).count() / 100);
        }
    }

    private double calculateGoodness() {
        OptionalDouble goodness = congressionalDistricts.stream()
                .mapToDouble(CongressionalDistrict::getGoodness).average();
        return goodness.isPresent() ? goodness.getAsDouble() : 0;
    }

    CongressionalDistrict getStartingDistrict() {
        return getRandomDistrict();
    }

    double getGoodness() {
        return this.goodness = calculateGoodness();
    }

    private CongressionalDistrict getRandomDistrict() {
        return congressionalDistricts.get((int) (Math.random() * congressionalDistricts.size()));
    }

    @Override
    public Object clone() {
        State state = new State();
        state.name = this.name;
        state.stat = this.stat;
        state.goodness = this.goodness;
        state.congressionalDistricts = this.congressionalDistricts;
        return state;
    }

    public void setBorderStatus() {
        for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
            List<Precinct> precincts = congressionalDistrict.getPrecincts();
            for (Precinct precinct : precincts) {
                precinct.setBorder();
            }
        }
    }

    public void setLockedPrecincts(ArrayList<Precinct> lockedPrecincts) {
        for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
            List<Precinct> precincts = congressionalDistrict.getPrecincts();
            for (Precinct precinct : precincts) {
                if (lockedPrecincts.contains(precinct)) {
                    precinct.setLocked(true);
                }
            }
        }
    }

    public void setLockedDistricts(ArrayList<CongressionalDistrict> lockedDistricts) {
        for (CongressionalDistrict congressionalDistrict : congressionalDistricts) {
            if (lockedDistricts.contains(congressionalDistrict)) {
                List<Precinct> precincts = congressionalDistrict.getPrecincts();
                for (Precinct precinct : precincts) {
                    precinct.setLocked(true);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "State{name=" + name
                + "congressionalDistricts=" + congressionalDistricts
                + '}';
    }
}
