package com.orioles.controller;

import com.orioles.districtgeneration.Measure;
import com.orioles.model.Move;
import com.orioles.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AlgoController {
	@Autowired
	private HttpSession httpSession;

	@PostMapping("/startAlgo")
	public List<Move> register (@RequestParam Map<String, String> settings) {
		State state = (State) httpSession.getAttribute("state");
		Map<Measure, Double> measures = new HashMap<>();
//		settings.keySet().forEach(str -> measures.put());
//		state.st

		List<Move> moves = new ArrayList<>();
		moves.add(new Move());
		moves.add(new Move());
		moves.add(new Move());
		moves.add(new Move());

		return moves;
	}
}
