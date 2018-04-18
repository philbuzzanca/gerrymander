package com.orioles.controller;

import com.orioles.districtgeneration.AllMeasures;
import com.orioles.model.Algorithm;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Move;
import com.orioles.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class AlgoController {
	@Autowired
	private HttpSession httpSession;

	@PostMapping("/startAlgo")
	public List<CongressionalDistrict> startAlgo (@RequestParam Map<String, String> settings) {
		State state = (State) httpSession.getAttribute("state");
		Map<AllMeasures, Double> measures = new HashMap<>();
		for (String measure : settings.keySet()) {
			measures.put(AllMeasures.valueOf(measure), Double.parseDouble(settings.get(measure)));
		}

		Algorithm algo = new Algorithm(state, measures, Collections.emptyList());
		httpSession.setAttribute("algo", algo);
		return state.getGerrymanderedDistricts();
	}

	@PostMapping("/runIteration")
	public List<Move> nextIteration () {
		Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
		algo.startAlgorithm();
		return algo.getMoves();
	}
}
