package orioles.model;

import orioles.constants.Party;
import orioles.constants.Race;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CongressionalDistrict implements Cloneable {
    private List<Precinct> precincts;
    private String name;
    private double oldGoodness;

    private boolean hasUpdated;
    private Stats stat;

	public CongressionalDistrict(List<Precinct> precincts, String name) {
		this.precincts = precincts;
		this.name = name;
		this.oldGoodness = -1;
		this.hasUpdated = false;
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

	public double getArea() {
		return 0;
	}

	public double getPerimeter() {
		return 0;
	}

	public Stats summarize(){
		if (!hasUpdated)
			return stat;

		Map<Race, Long> conDistRace = new HashMap<>();
		Map<Party, Long> conDistParty = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0);

        precincts.stream()
				.map(Precinct::getStats)
				.forEach(precinctStat -> Summary.summarize(conDistRace, conDistParty, precinctStat, stat));

        hasUpdated = true;
        return stat;
    }
    
    public Precinct getStartingPrecinct(){
        return null;
    }

	public Precinct choosePrecinct(){
		return null;
	}
    
    public Precinct removeFromDistrict(Precinct precinct){
		hasUpdated = false;
        precincts.remove(precinct);
        return precinct;
    }
    
    public void addToDistrict(Precinct precinct){
		hasUpdated = false;
		precincts.add(precinct);
    }
    
    public Precinct getPrecinctById(int precinctId){
        return precincts.stream()
				.filter(precinct -> precinct.getIdentifier() == precinctId)
				.findFirst().orElse(null);
    }

	public Precinct getRandomPrecinct(){
		int random = (int)(Math.random()*precincts.size());
		return precincts.get(random);
	}
}
