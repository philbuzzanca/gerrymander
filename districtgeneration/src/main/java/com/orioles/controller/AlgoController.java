package com.orioles.controller;

import com.orioles.constants.Constants;
import com.orioles.districtgeneration.AllMeasures;
import com.orioles.districtgeneration.Constraint;
import com.orioles.model.Algorithm;
import com.orioles.model.Move;
import com.orioles.model.Pair;
import com.orioles.model.State;
//import com.orioles.persistence.StateRepository;
import com.orioles.persistence.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class AlgoController {
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private HttpSession httpSession;

	@PostMapping("/startAlgo")
	public List<Pair<Integer, Double>> startAlgo (@RequestParam Map<String, String> settings) {
		Map<AllMeasures, Integer> measures = new HashMap<>();
		Map<Constraint, Boolean> constraints = new HashMap<>();

		for (String key : settings.keySet()) {
			String[] line = settings.get(key).split("\\[");
			switch (line[0]) {
				case Constants.MEASURE:
					String label = key.substring(Constants.MEASURE_LENGTH + 1, key.length() - 1);
					measures.put(AllMeasures.valueOf(label.toUpperCase()), Integer.parseInt(settings.get(key)));
					break;
				case Constants.CONSTRIANT:
					label = key.substring(Constants.CONSTRAINT_LENGTH + 1, key.length() - 1);
					constraints.put(Constraint.valueOf(label.toUpperCase()), settings.get(key).equals("on"));
					break;
				default:
			}
		}

//		State geoState = (State) httpSession.getAttribute("state");
		State geoState = stateRepository.findByName("state").get(0);
		Algorithm algo = new Algorithm(geoState, measures, Collections.emptyList());
		httpSession.setAttribute("algo", algo);
		return geoState.getGerrymanderedDistricts();
	}

	@PostMapping("/runIteration")
	public List<Move> nextIteration () {
		Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
		algo.runAlgorithm();
		return algo.getCurrMoves();
	}
}
