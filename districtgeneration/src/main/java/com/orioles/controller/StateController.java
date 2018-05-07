package com.orioles.controller;

import com.google.gson.Gson;
import com.orioles.constants.Constants;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;
import com.orioles.exceptions.NoSuchStateException;
import com.orioles.helper_model.Polygon;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.FeatureCollection;
import com.orioles.model.Precinct;
import com.orioles.model.State;
import com.orioles.persistence.PDemoRepository;
import com.orioles.persistence.PrecinctRepository;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {
    private Map<String, FeatureCollection> stateCache;
    @Autowired
    private Gson gson;
    @Autowired
    private PrecinctRepository precinctRepository;
	@Autowired
	private PDemoRepository pDemoRepository;
    @Autowired
    private Environment environment;
    private Map<String, State> states;
    private Map<Integer, Precinct> allPrecincts;

    @GetMapping("/state/{name}")
    public State getState(@PathVariable("name") String stateName) {
        List<State> states = Collections.emptyList();   // stateRepository.findByName(stateName.toLowerCase());
        if (states.isEmpty() || states.size() > 1) {
            throw new NoSuchStateException(environment.getProperty("orioles.state.nomatch"));
        }
        return (State) (states.get(0).clone());
    }
    
    private FeatureCollection getFromCache(String stateName){
        if (stateCache == null) {
            stateCache = new HashMap<>();
        }
        return stateCache.getOrDefault(stateName, null);
    }

    @GetMapping("/precincts/{state}")
    public FeatureCollection getPrecincts(@PathVariable("state") String stateName) {
        stateName = stateName.toLowerCase();
        FeatureCollection result = getFromCache(stateName);
        if (result != null){
            return result;
        }
        List<Precinct> precincts = precinctRepository.findByIdState(stateName);
        result = new FeatureCollection();
        List<Map> features = new ArrayList<>();
        if (precincts.isEmpty())
            throw new NoSuchStateException(environment.getProperty("orioles.state.nomatch"));
        for (Precinct precinct : precincts) {
            Map feature = gson.fromJson(precinct.getGeojson(), Map.class);
            features.add(feature);
        }
        result.setFeatures(features);
        stateCache.put(stateName, result);
        return result;
    }

	@GetMapping("/getVa")
	public State getVa() {
		return getStateByName("va");
	}

	private State getStateByName(String stateName) {
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

				Map json = gson.fromJson(p.getGeojson(), Map.class);
				p.setCoordinates(parseCoordinates(((Map)json.get(Constants.GEOMETRY)).get(Constants.COORDINATES)));
				allPrecincts.put(p.getIdentifier(), p);
			}
			cds.add(cd);
		}

		State s = new State(cds, stateName);
		for (Precinct p : s.getAllPrecincts()) {
			Map json = gson.fromJson(p.getGeojson(), Map.class);
			p.setAdjacentPrecincts(parseAdjacent(((Map)json.get(Constants.PROPERTIES)).get(Constants.NEIGHBORS)));
		}

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

	@SuppressWarnings("unchecked")
	private List<Precinct> parseAdjacent(Object adj) {
		if (adj == null)
			return Collections.emptyList();
		return Arrays.stream(((String) adj).split(","))
				.map(e -> allPrecincts.get(Integer.parseInt(e))).collect(Collectors.toList());
	}
}
