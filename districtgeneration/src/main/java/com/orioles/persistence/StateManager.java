package com.orioles.persistence;

import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Precinct;
import com.orioles.model.State;
import com.orioles.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;

public class StateManager {
	@Autowired
	private PrecinctRepository precinctRepository;
	@Autowired
	private PDemoRepository pDemoRepository;
	private Map<String, State> states;
	private static StateManager instance;

	private StateManager() {
		this.states  = new HashMap<>();
	}

	public static StateManager getSingleton() {
		if (instance == null)
			instance = new StateManager();
		return instance;
	}

	public State getStateByName(String stateName) {
		if (states.containsKey(stateName))
			return (State) states.get(stateName).clone();

		List<Precinct> precinctList = precinctRepository.findByIdState(stateName);
		List<CongressionalDistrict> cds = new ArrayList<>();
		for(int distID : precinctList.stream().mapToInt(p -> p.getId().getCd()).distinct().toArray()) {
			List<Precinct> precinctInCD = precinctList.stream()
					.filter(p -> p.getId().getCd() == distID)
					.collect(Collectors.toList());
			cds.add(new CongressionalDistrict(precinctInCD, distID));
		}

		State s = new State(cds, stateName);
		states.put(stateName, s);
		return s;
	}


}
