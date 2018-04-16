package orioles.model;

import orioles.constants.Party;
import orioles.constants.Race;

import java.util.Map;

public class Stats {
    private Map<Race, Long> races;
    private Map<Party, Long> parties;
    private long population;

	Stats(Map<Race, Long> races, Map<Party, Long> parties, long population) {
		this.races = races;
		this.parties = parties;
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

    long getPopulation() {
        return population;
    }

    void setPopulation(long population) {
        this.population = population;
    }    
}
