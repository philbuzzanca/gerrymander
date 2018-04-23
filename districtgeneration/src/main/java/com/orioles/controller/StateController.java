package com.orioles.controller;

import com.google.gson.Gson;
import com.orioles.exceptions.NoSuchStateException;
import com.orioles.model.FeatureCollection;
import com.orioles.model.Precinct;
import com.orioles.model.State;
import com.orioles.persistence.PrecinctRepository;
import com.orioles.persistence.StateRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private StateRepository stateRepository;
    @Autowired
    private Environment environment;

    @GetMapping("/state/{name}")
    public State getState(@PathVariable("name") String stateName) {
        stateName = stateName.toLowerCase();
        List<State> states = stateRepository.findByName(stateName);
        if (states.isEmpty() || states.size() > 1) {
            throw new NoSuchStateException(environment.getProperty("orioles.state.nomatch"));
        }
        return (State) (states.get(0).clone());
    }
    
    public FeatureCollection getFromCache(String stateName){
        if (stateCache == null) {
            stateCache = new HashMap<>();
        }
        if (stateCache.containsKey(stateName)){
            return stateCache.get(stateName);
        }
        return null;
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
}
