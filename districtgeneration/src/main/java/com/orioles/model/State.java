package com.orioles.model;

import com.orioles.constants.Constants;
import com.orioles.constants.Party;
import com.orioles.constants.Race;
import com.orioles.districtgeneration.AllMeasures;
import com.orioles.districtgeneration.Measure;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GeoState")
public class State implements Cloneable, Serializable {
    @Transient
    private List<CongressionalDistrict> congressionalDistricts;
    @Id
    @Column(name="name",
            columnDefinition="VARCHAR(255) PRIMARY KEY")
    private String name;
    @Column(name="geo_json",
            columnDefinition="LONGTEXT NOT NULL")
    private String geoJson;
    @Transient
    private double goodness;
    @Transient
    private boolean hasUpdated;
    @Transient
    private Stats stat;

    public State() {
        congressionalDistricts = new ArrayList<>();
        name = "";
        goodness = 0;
        hasUpdated = false;
        stat = new Stats();
        geoJson = "{}";
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

    public void setGoodness(double newGoodness) {
        this.goodness = newGoodness;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public void addDistrict(CongressionalDistrict cd) {
        congressionalDistricts.add(cd);
    }

    public Stats summarize() {
        if (!hasUpdated) {
            return stat;
        }

        Map<Race, Long> conDistRace = new HashMap<>();
        Map<Party, Long> conDistParty = new HashMap<>();
        stat = new Stats(conDistRace, conDistParty, 0);

        congressionalDistricts.stream()
                .map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Stats.summarize(conDistRace, conDistParty, cdStat, stat));

        hasUpdated = true;
        return stat;
    }

    public CongressionalDistrict getDistrictByID(int districtID) {
        return congressionalDistricts.stream()
                .filter(district -> districtID == district.getID())
                .findFirst().orElse(null);
    }

    public List<CongressionalDistrict> getGerrymanderedDistricts() {
        return congressionalDistricts.stream()
                .filter(district -> district.getGoodness() < Constants.GERRYMANDERING_THRESHOLD)
                .collect(Collectors.toList());
    }

    void calculateDistrictGoodness(Map<AllMeasures, Double> measures) {
        for (CongressionalDistrict cd : congressionalDistricts) {
            List<Double> goodnessVals = new ArrayList<>();
            measures.keySet().forEach(key -> goodnessVals.add(key.calculateGoodness(cd) * measures.get(key)));
            OptionalDouble goodness = goodnessVals.stream().mapToDouble(num -> num).average();
            if (goodness.isPresent()) {
                cd.setGoodness(goodness.getAsDouble());
            }
        }
    }

    double getGoodness() {
        OptionalDouble goodness = congressionalDistricts.stream()
                .mapToDouble(CongressionalDistrict::getGoodness).average();
        return this.goodness = goodness.isPresent() ? goodness.getAsDouble() : 0;
    }

    public CongressionalDistrict getStartingDistrict() {
        return getRandomDistrict();
    }

    private CongressionalDistrict getRandomDistrict() {
        return congressionalDistricts.get((int) (Math.random() * congressionalDistricts.size()));
    }

    @Override
    public Object clone() {
        State state = new State();
        state.name = this.name;
        state.geoJson = this.geoJson;
        state.stat = this.stat;
        state.goodness = this.goodness;
        state.congressionalDistricts = this.congressionalDistricts;
        return state;
    }
}
