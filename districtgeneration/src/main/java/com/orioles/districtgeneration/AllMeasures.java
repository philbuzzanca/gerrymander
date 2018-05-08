package com.orioles.districtgeneration;

import com.orioles.constants.Race;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.State;
import com.orioles.model.Stats;

import java.util.Map;
import java.util.Set;

import static com.orioles.constants.Race.WHITE;

public enum AllMeasures implements Measure {
	COMPACTNESS {    // Schwartzberg
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			double r = Math.sqrt(district.getArea() / Math.PI);
			double equalAreaPerimeter = 2 * Math.PI * r;
			return equalAreaPerimeter / district.getPerimeter();
		}
	},COMPACTNESS2 {    // PolsbyPopper
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			return (4 * Math.PI * district.getArea()) / Math.pow(district.getPerimeter(), 2);
		}
	}, EQUAL_POPULATION {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			Stats stateStat = state.summarize();
			long expPopulation = (long)(stateStat.getPopulation() *
					((double) district.getNumPrecincts() / state.getNumPrecincts()));
			long population = district.summarize().getPopulation();
			return population < expPopulation ? population / expPopulation : expPopulation / population;
		}
	}, RACIAL_FAIRNESS {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			// TODO: Fill in an approach considering all races
			Map<Race, Long> cdRaces = district.summarize().getRaces();
			Map<Race, Long> stateRaces = state.summarize().getRaces();

			double cdWhitePop = 0;
			double cdOtherPop = 0;
			Set<Race> keyset= cdRaces.keySet();
			for(Race dem : keyset){
				if(dem==WHITE){
					cdWhitePop+=cdRaces.get(dem);
				}
				else
					cdOtherPop+=cdRaces.get(dem);
			}

			double stateWhitePop = 0;
			double stateOtherPop = 0;
			for(Race dem : keyset){
				if(dem==WHITE){
					stateWhitePop+=stateRaces.get(dem);
				}
				else
					stateOtherPop+=stateRaces.get(dem);
			}

			double stateRatio = stateOtherPop/stateWhitePop;
			double cdRatio = cdOtherPop/cdWhitePop;

			if(cdRatio<stateRatio)
				return cdRatio/stateRatio;
			else
				return stateRatio/cdRatio;
		}
	}, PARTISAN_FAIRNESS {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			return 0;
		}
	}
}
