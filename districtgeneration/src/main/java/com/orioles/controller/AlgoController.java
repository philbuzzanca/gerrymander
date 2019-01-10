package com.orioles.controller;

import com.google.gson.Gson;
import com.orioles.constants.Constants;
import com.orioles.districtgeneration.AllMeasures;
import com.orioles.districtgeneration.Constraint;
import com.orioles.exceptions.NoSuchStateException;
import com.orioles.helper_model.Pair;
import com.orioles.model.*;
import com.orioles.persistence.PrecinctRepository;
import com.orioles.persistence.UsermovesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AlgoController {
    @Autowired
    private PrecinctRepository precinctRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private Environment environment;
    @Autowired
    private Gson gson;
    @Autowired
    private UsermovesRepository usermovesRepository;

    @PostMapping("/startAlgo")
    public Map<Integer, Double> startAlgo(@RequestParam Map<String, String> settings) {
        String state = "Select a state...";
        Map<AllMeasures, Integer> measures = new HashMap<>();
        Map<Constraint, Boolean> constraints = new HashMap<>();

        for (String key : settings.keySet()) {
            String[] line = key.split("\\[");
            switch (line[0]) {
                case Constants.MEASURE:
                    String label = key.substring(Constants.MEASURE_LENGTH + 1, key.length() - 1);
                    measures.put(AllMeasures.valueOf(label.toUpperCase()), Integer.parseInt(settings.get(key)));
                    break;
                case Constants.CONSTRIANT:
                    label = key.substring(Constants.CONSTRAINT_LENGTH + 1, key.length() - 1);
                    constraints.put(Constraint.valueOf(label.toUpperCase()), settings.get(key).equals("on"));
                    break;
                case Constants.STATE:
                    state = settings.get(key);
                    break;
                default:
                    System.out.printf("Invalid arg: %s%n", line[0]);
                    throw new NoSuchStateException(environment.getProperty(Constants.NO_MATCH));
            }
        }

		if (state.equals("Select a state...")) {
			throw new NoSuchStateException(environment.getProperty(Constants.NO_MATCH));
		}

		State geoState = (State) httpSession.getAttribute(Constants.STATE);
		System.out.printf("%n%n%s%b%n", state, geoState == null);
		System.out.println(geoState.getName());
		Algorithm algo = new Algorithm(geoState, measures, constraints);
		algo.setup();
		httpSession.setAttribute("algo", algo);
		return geoState.getGerrymanderedDistricts();
	}

    @PostMapping("/runIteration")
    public List<Move> nextIteration() {
        Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
        algo.runAlgorithm();
        httpSession.setAttribute("algo", algo);
        return algo.getCurrMoves();
    }
    
    @GetMapping("/saveMoves")
    public String saveMoves() {
        Usermoves moveList = new Usermoves();
        final String userAttribute = environment.getProperty("orioles.session.user");
        Algorithm algo = (Algorithm) httpSession.getAttribute("algo");
        String moveListJson = gson.toJson(algo.getMasterMoves());
        User user = (User) httpSession.getAttribute("user");
        moveList.setMoves(moveListJson);
        moveList.setUsername(user.getUsername());
        usermovesRepository.save(moveList);
        return environment.getProperty("orioles.statuscode.success");
    }
}
