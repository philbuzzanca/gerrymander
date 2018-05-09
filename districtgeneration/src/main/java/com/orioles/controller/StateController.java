package com.orioles.controller;

import com.google.gson.Gson;
import com.orioles.constants.Constants;
import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;
import com.orioles.exceptions.NoSuchStateException;
import com.orioles.helper_model.Polygon;
import com.orioles.model.*;
import com.orioles.persistence.PDemoRepository;
import com.orioles.persistence.PrecinctRepository;
import java.util.*;
import java.util.stream.Collectors;

import com.orioles.persistence.UsermovesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class StateController {
    private Map<String, FeatureCollection> stateCache;
    @Autowired
    private Gson gson;
    @Autowired
    private PrecinctRepository precinctRepository;
	@Autowired
	private UsermovesRepository userMovesRepository;
	@Autowired
	private PDemoRepository pDemoRepository;
    @Autowired
    private Environment environment;
	@Autowired
	private HttpSession httpSession;
    private Map<String, State> states;
    private Map<Integer, Precinct> allPrecincts;

    @GetMapping("/state/{name}")
    public State getState(@PathVariable("name") String stateName) {
    	if (!Constants.ALL_STATES.contains(stateName.toLowerCase()))
    		throw new NoSuchStateException(environment.getProperty(Constants.NO_MATCH));
        return getStateByName(stateName.toLowerCase());
    }
    
    private FeatureCollection getFromCache(String stateName){
        if (stateCache == null)
            stateCache = new HashMap<>();
        return stateCache.getOrDefault(stateName, null);
    }

    @GetMapping("/precincts/{state}")
    public FeatureCollection getPrecincts(@PathVariable(Constants.STATE) String stateName) {
        stateName = stateName.toLowerCase();
        FeatureCollection result = getFromCache(stateName);
        if (result != null)
            return result;

        List<Precinct> precincts = precinctRepository.findByIdState(stateName);
		if (precincts.isEmpty())
			throw new NoSuchStateException(environment.getProperty(Constants.NO_MATCH));

        result = new FeatureCollection(precincts.stream()
				.map(p -> gson.fromJson(p.getGeojson(), Map.class)).collect(Collectors.toList()));
        stateCache.put(stateName, result);
        return result;
    }

	@PostMapping("/getMaps")
	public int getMaps (String user) {
    	return userMovesRepository.findByUsername(user).size();
	}

    @SuppressWarnings("unchecked")
	@PostMapping("/loadMap")
	public List<Move> loadMap (String currState, int mapID) {
		Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
		algo.loadOldRedistricting(getStateByName(currState.toLowerCase()),
				(List<Move>) gson.fromJson(userMovesRepository.findByUselessid(mapID).getMoves(), List.class));
		httpSession.setAttribute("algo", algo);
		return algo.getCurrMoves();
	}

	@GetMapping("/getStateByName")
	public State getStateName(String stateName) {
		return getStateByName(stateName.toLowerCase());
	}

	private State checkCache(String stateName) {
    	if (states == null)
    		states = new HashMap<>();
    	if (allPrecincts == null)
    		allPrecincts = new HashMap<>();
    	return states.containsKey(stateName) ? (State) states.get(stateName).clone() : null;
	}

	private State getStateByName(String stateName) {
		State fromCache = checkCache(stateName.toLowerCase());
    	if (fromCache != null) {
			httpSession.setAttribute(Constants.STATE, fromCache);
			return fromCache;
		}

		List<Precinct> precinctList = precinctRepository.findByIdState(stateName);

		List<CongressionalDistrict> cds = new ArrayList<>();
		for(int distID : precinctList.stream().mapToInt(Precinct::getCD).distinct().toArray()) {
			CongressionalDistrict cd = new CongressionalDistrict(distID);
			List<Precinct> precinctInCD = precinctList.stream()
					.filter(p -> p.getCD() == distID).collect(Collectors.toList());

			cd.setPrecincts(precinctInCD);
			precinctInCD.forEach(p -> setupPrecinct(p, cd));
			cds.add(cd);
		}

		State s = new State(cds, stateName);
		processAdj(s);

		states.put(stateName, s);
		httpSession.setAttribute(Constants.STATE, s);
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

	private void setupPrecinct(Precinct p, CongressionalDistrict cd) {
		p.setDistrict(cd);
		p.setStats(pDemoRepository.findByPid(p.getIdentifier()).makeStat());

		Map json = gson.fromJson(p.getGeojson(), Map.class);
		p.setCoordinates(parseCoordinates(((Map)json.get(Constants.GEOMETRY)).get(Constants.COORDINATES)));
		allPrecincts.put(p.getIdentifier(), p);
	}

	private void processAdj(State s) {
		for (Precinct p : s.getAllPrecincts()) {
			Map json = gson.fromJson(p.getGeojson(), Map.class);
			p.setAdjacentPrecincts(parseAdjacent(((Map)json.get(Constants.PROPERTIES)).get(Constants.NEIGHBORS)));
		}
	}

	@SuppressWarnings("unchecked")
	private List<Precinct> parseAdjacent(Object adj) {
		if (adj == null)		// ie: islands
			return Collections.emptyList();
		return Arrays.stream(((String) adj).split(","))
				.map(e -> allPrecincts.get(Integer.parseInt(e))).collect(Collectors.toList());
	}
}
