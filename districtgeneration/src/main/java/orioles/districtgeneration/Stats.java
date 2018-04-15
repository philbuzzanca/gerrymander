package orioles.districtgeneration;

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

	public Map<Race, Long> getRaces() {
        return races;
    }

    public void setRaces(Map<Race, Long> races) {
        this.races = races;
    }

    public Map<Party, Long> getParties() {
        return parties;
    }

    public void setParties(Map<Party, Long> parties) {
        this.parties = parties;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }    
}
