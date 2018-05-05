package com.orioles.persistence;

import com.google.gson.Gson;
import com.orioles.constants.Constants;
import com.orioles.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;

public class StateManager {
	private Gson gson;
	private PrecinctRepository precinctRepository;
	private PDemoRepository pDemoRepository;
	private Map<String, State> states;
	private static StateManager instance;
	private static boolean isSetup;

	private StateManager() {
		this.states  = new HashMap<>();
	}

	public static StateManager getStateManager() {
		return instance == null ? instance = new StateManager() : instance;
	}

	public static void setupManager(Gson gson, PrecinctRepository precinctRepository, PDemoRepository pDemoRepository) {
		if (isSetup)
			return;
		StateManager sm = getStateManager();
		sm.gson = gson;
		sm.precinctRepository = precinctRepository;
		sm.pDemoRepository = pDemoRepository;
		isSetup = true;
	}

	public State getStateByName(String stateName) {
		if (states.containsKey(stateName))
			return (State) states.get(stateName).clone();
		List<Precinct> precinctList = precinctRepository.findByIdState(stateName);
		List<CongressionalDistrict> cds = new ArrayList<>();
		for(int distID : precinctList.stream().mapToInt(p -> p.getId().getCd()).distinct().toArray()) {
			CongressionalDistrict cd = new CongressionalDistrict();
			cd.setID(distID);
			List<Precinct> precinctInCD = precinctList.stream()
					.filter(p -> p.getId().getCd() == distID).collect(Collectors.toList());

			System.out.printf("Found %d precincts in district id:%d%n", precinctInCD.size(), distID);
			cd.setPrecincts(precinctInCD);
			for (Precinct p : precinctInCD) {
				p.setDistrict(cd);
				System.out.printf("%sPdemo (%d): %s%s%n", Constants.ANSI_GREEN,
						p.getId().getId(), pDemoRepository.findByPid(p.getId().getId()), Constants.ANSI_RESET);
				p.setStats(pDemoRepository.findByPid(p.getId().getId()).makeStat());
				Object geometry = gson.fromJson(p.getGeojson(), Map.class).get("geometry");

				System.out.println(((Map)geometry).get("coordinates").getClass().getName());
				System.out.println(((Map)geometry).get("coordinates").toString());

				// TBD: fill in precinct coordinates
				break;
			}
			cds.add(cd);
			break;
		}

		State s = new State(cds, stateName);
		states.put(stateName, s);
		return s;
	}


}
