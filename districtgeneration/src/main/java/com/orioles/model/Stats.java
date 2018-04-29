package com.orioles.model;

import com.orioles.constants.Party;
import com.orioles.constants.Race;
import java.util.Map;

public class Stats {
    private Map<Race, Long> races;
    private Map<Party, Long> parties;
    private long population;

    
    public Stats(){
        
    }
    
	Stats(Map<Race, Long> races, Map<Party, Long> parties, long population) {
		this.races = races;
		this.parties = parties;
		this.population = population;
	}

    public Map<Race, Long> getRaces() {
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
    
    

	static void summarize(Map<Race, Long> races, Map<Party, Long> parties, Stats eachStat, Stats overallStats) {
		for (Race r : Race.values()) {
			races.put(r, races.get(r) + eachStat.getRaces().get(r));
		}

		for (Party p : Party.values()) {
			parties.put(p, parties.get(p) + eachStat.getParties().get(p));
		}

		overallStats.setPopulation(overallStats.getPopulation() + eachStat.getPopulation());
	}
}
