package com.orioles.model;

import com.orioles.constants.Demographic;
import com.orioles.constants.Party;
import com.orioles.constants.Race;
import java.util.Map;

public class Stats {
    private Map<Race, Long> races;
    private Map<Party, Long> parties;
    private Map<Demographic, Long> demographics;
    private long population;

    
    public Stats(){
        
    }
    
	Stats(Map<Race, Long> races, Map<Party, Long> parties, long population, Map<Demographic, Long> demographics) {
		this.races = races;
		this.parties = parties;
                this.demographics = demographics;
		this.population = population;
	}

	Map<Race, Long> getRaces() {
        return races;
    }

    void setRaces(Map<Race, Long> races) {
        this.races = races;
    }

    Map<Party, Long> getParties() {
        return parties;
    }

    void setParties(Map<Party, Long> parties) {
        this.parties = parties;
    }

    public long getPopulation() {
        return population;
    }

    void setPopulation(long population) {
        this.population = population;
    }
    
    public Map<Demographic, Long> getDemographics() {
        return demographics;
    }

    void setDemographics(Map<Demographic, Long> demographics) {
        this.demographics = demographics;
    }
    
    

	static void summarize(Map<Race, Long> races, Map<Party, Long> parties, Stats eachStat, Stats overallStats,Map<Demographic, Long> demographics) {
		for (Race r : Race.values()) {
			races.put(r, races.get(r) + eachStat.getRaces().get(r));
		}

		for (Party p : Party.values()) {
			parties.put(p, parties.get(p) + eachStat.getParties().get(p));
		}
                
                for (Demographic d : Demographic.values()) {
			demographics.put(d, demographics.get(d) + eachStat.getDemographics().get(d));
		}

		overallStats.setPopulation(overallStats.getPopulation() + eachStat.getPopulation());
	}
}
