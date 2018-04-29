/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orioles.districtgeneration;

import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Measure;
import com.orioles.model.State;
import com.orioles.model.Stats;


public class PopulationMeasure implements Measure{

    @Override
    public double calculateGoodness(State state) {
        double sum =0;
        for (CongressionalDistrict congressionalDistrict : state.getCongressionalDistricts()) {
            sum+= calculateGoodness(congressionalDistrict, state);
        }
        
        return sum/state.getCongressionalDistricts().size();
    }

    @Override
    public double calculateGoodness(CongressionalDistrict district, State state) {
        Long statePopulation = state.getStats().getPopulation();
        Long avgPopulation = statePopulation/state.getCongressionalDistricts().size();
        Long population = district.getStats().getPopulation();
        if(population>avgPopulation)
            return population/avgPopulation;
        else
            return avgPopulation/population;
        
    }

    @Override
    public double normalize(double measure) {
        return 0;
    }
    
}
