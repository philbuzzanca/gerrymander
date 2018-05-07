package com.orioles.persistence;

import com.google.gson.Gson;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;
import com.orioles.helper_model.Polygon;
import com.orioles.model.*;
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
		for(int distID : precinctList.stream().mapToInt(Precinct::getCD).distinct().toArray()) {
			CongressionalDistrict cd = new CongressionalDistrict(distID);
			List<Precinct> precinctInCD = precinctList.stream()
					.filter(p -> p.getCD() == distID).collect(Collectors.toList());

			cd.setPrecincts(precinctInCD);
			for (Precinct p : precinctInCD) {
				p.setDistrict(cd);
				p.setStats(pDemoRepository.findByPid(p.getIdentifier()).makeStat());
				p.setCoordinates(parseCoordinates(((Map)gson.fromJson(p.getGeojson(), Map.class)
						.get("geometry")).get("coordinates")));
			}
			cds.add(cd);
		}

		State s = new State(cds, stateName);
		states.put(stateName, s);
		return s;
	}

	@SuppressWarnings("unchecked")
	private List<Polygon> parseCoordinates(Object coords) {		// Assumes coords is a multi-polygon
		List<List<List<List<Double>>>> coordinates = (List<List<List<List<Double>>>>) coords;
		return coordinates.stream().map(poly ->
				new Polygon(poly.stream().map(this::obtainEdges).collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

	private List<Edge> obtainEdges(List<List<Double>> rawEdges) {
		List<Edge> edges = new ArrayList<>();
		final List<Coordinate> coordList = rawEdges.stream()
				.map(pt -> new Coordinate(pt.get(0), pt.get(1))).collect(Collectors.toList());
		for (int i = 0; i < coordList.size(); i++)
			edges.add(new Edge(coordList.get(i), coordList.get((i + 1) % coordList.size())));
		return edges;
	}
}
