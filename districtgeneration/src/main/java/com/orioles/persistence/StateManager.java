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

	@SuppressWarnings("unchecked")
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

			cd.setPrecincts(precinctInCD);
			for (Precinct p : precinctInCD) {
				p.setDistrict(cd);
				p.setStats(pDemoRepository.findByPid(p.getIdentifier()).makeStat());
//				System.out.println(p.getStats());

				List<List<List<List<Double>>>> coordinates = (List<List<List<List<Double>>>>)
						((Map)gson.fromJson(p.getGeojson(), Map.class).get("geometry")).get("coordinates");
				if (coordinates.get(0).size() > 1)
					System.out.printf("L1:%d; L2:%d; L3:%d; L4:%f%n", coordinates.size(), coordinates.get(0).size(),
							coordinates.get(0).get(0).size(), coordinates.get(0).get(0).get(0).get(0));
//				System.out.println("----------------1");
//				System.out.println(coordinates.get(0).toString());
//				System.out.println("----------------2");
//				System.out.println(coordinates.get(0).get(0).toString());
//				System.out.println("----------------3");
//				System.out.println(coordinates.get(0).get(0).get(0).toString());

				// TBD: fill in precinct coordinates
//				break;
			}
			cds.add(cd);
//			break;
		}

		State s = new State(cds, stateName);
		states.put(stateName, s);
		return s;
	}

//	private
}
