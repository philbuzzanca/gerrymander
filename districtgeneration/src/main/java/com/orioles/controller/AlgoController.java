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
	public List<Integer> startAlgo (@RequestParam Map<String, String> settings,
												  @RequestParam String state) {
		System.out.println("------------------------------------------");
		for (String s : settings.keySet())
			System.out.printf("%s -> %s%n", s, settings.get(s));

		List<Integer> gerrymandered = new ArrayList<>();
		gerrymandered.add(123);
		gerrymandered.add(234);

		State geoState = (State) httpSession.getAttribute(state);
		System.out.println(geoState == null ? "geoState null" : "Where did geoState come from");
		return gerrymandered;

//		Map<AllMeasures, Double> measures = new HashMap<>();
//		settings.keySet().forEach(key -> measures.put(AllMeasures.valueOf(key), Double.parseDouble(settings.get(key))));
//
//		Algorithm algo = new Algorithm(geoState, measures, Collections.emptyList());
//		httpSession.setAttribute("algo", algo);
//		return geoState.getGerrymanderedDistricts();
	}

	@PostMapping("/runIteration")
	public List<Move> nextIteration () {
		Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
		algo.startAlgorithm();
		return algo.getMoves();
	}
}
