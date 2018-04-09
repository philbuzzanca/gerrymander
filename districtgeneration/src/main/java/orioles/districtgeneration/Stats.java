package orioles.districtgeneration;

import java.util.Map;

public class Stats {
    private Map<Race, Double> races;
    private Map<Party, Double> parties;
    private int population;

    public Map<Race, Double> getRaces() {
        return races;
    }

    public void setRaces(Map<Race, Double> races) {
        this.races = races;
    }

    public Map<Party, Double> getParties() {
        return parties;
    }

    public void setParties(Map<Party, Double> parties) {
        this.parties = parties;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }    
}
