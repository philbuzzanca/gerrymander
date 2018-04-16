package orioles.model;

import orioles.constants.Party;
import orioles.constants.Race;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State implements Cloneable {
    private List<CongressionalDistrict> congressionalDistricts;
    private String name;

	private boolean hasUpdated;
	private Stats stat;

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
    
    public Stats summarize() {
		if (!hasUpdated)
			return stat;

        Map<Race, Long> conDistRace = new HashMap<>();
        Map<Party, Long> conDistParty = new HashMap<>();
		stat = new Stats(conDistRace, conDistParty, 0);

        congressionalDistricts.stream()
                .map(CongressionalDistrict::summarize)
                .forEach(cdStat -> Summary.summarize(conDistRace, conDistParty, cdStat, stat));

        hasUpdated = true;
		return stat;
    }
    
    public CongressionalDistrict getDistrictByName (String districtName){
        return congressionalDistricts.stream()
				.filter(precinct -> districtName.equals(precinct.getName()))
				.findFirst().orElse(null);
    }

//    public CongressionalDistrict getDistrictByNumber(int districtNumber){
//		return congressionalDistricts.stream()
//				.filter(precinct -> precinct.getIdentifier() == districtNumber)
//				.findFirst().orElse(null);
//    }
    
    public List<CongressionalDistrict> getGerrymanderedDistricts(){
        return null;
    }
}
