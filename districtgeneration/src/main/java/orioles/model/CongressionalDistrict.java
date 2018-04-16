package orioles.model;

import orioles.constants.Party;
import orioles.constants.Race;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CongressionalDistrict implements Cloneable {
    private List<Precinct> precincts;
    private String name;
    private double oldGoodness;

	public CongressionalDistrict(List<Precinct> precincts, String name) {
		this.precincts = precincts;
		this.name = name;
		this.oldGoodness = -1;
	}

	public List<Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(List<Precinct> precincts) {
		this.precincts = precincts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOldGoodness() {
		return oldGoodness;
	}

	public void setOldGoodness(double oldGoodness) {
		this.oldGoodness = oldGoodness;
	}

	public Stats summarize(){
		Map<Race, Long> conDistRace = new HashMap<>();
		Map<Party, Long> conDistParty = new HashMap<>();
		Stats CdStats = new Stats(conDistRace, conDistParty, 0);

        precincts.stream().map(Precinct::getStats).forEach(precinctStat -> {
			for (Race r : Race.values()) {
				conDistRace.put(r, conDistRace.get(r) + precinctStat.getRaces().get(r));
			}

			for (Party p : Party.values()) {
				conDistParty.put(p, conDistParty.get(p) + precinctStat.getParties().get(p));
			}

			CdStats.setPopulation(CdStats.getPopulation() + precinctStat.getPopulation());
		});

        return CdStats;
    }
    
    public Precinct choosePrecinct(){
        return null;
    }
    
    public Precinct getStartingPrecinct(){
        return null;
    }
    
    public Precinct removeFromDistrict(Precinct precinct){
        precincts.remove(precinct);
        return precinct;
    }
    
    public void addToDistrict(Precinct precinct){
		precincts.add(precinct);
    }
    
    public Precinct getPrecinctById(int precinctId){
        return precincts.stream()
				.filter(precinct -> precinct.getIdentifier() == precinctId)
				.findFirst().orElse(null);
    }
}
