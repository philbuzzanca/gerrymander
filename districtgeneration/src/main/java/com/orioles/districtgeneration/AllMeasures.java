package com.orioles.districtgeneration;

import com.orioles.constants.Race;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.State;
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

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}, EQUAL_POPULATION {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			Long statePopulation = state.getStats().getPopulation();
			Long avgPopulation = statePopulation/state.getCongressionalDistricts().size();
			Long population = district.summarize().getPopulation();
			if(population<avgPopulation)
				return population/avgPopulation;
			else
				return avgPopulation/population;

//			return district.summarize().getPopulation();
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}, RACIAL_FAIRNESS {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			// TODO: Fill in
			Map<Race, Long> cdDemographics = district.summarize().getRaces();
			Map<Race, Long> stateDemographics = state.getStats().getRaces();

			double cdWhitePop = 0;
			double cdOtherPop = 0;
			Set<Race> keyset= cdDemographics.keySet();
			for(Race dem : keyset){
				if(dem==WHITE){
					cdWhitePop+=cdDemographics.get(dem);
				}
				else
					cdOtherPop+=cdDemographics.get(dem);
			}

			double stateWhitePop = 0;
			double stateOtherPop = 0;
			for(Race dem : keyset){
				if(dem==WHITE){
					stateWhitePop+=stateDemographics.get(dem);
				}
				else
					stateOtherPop+=stateDemographics.get(dem);
			}

			double stateRatio = stateOtherPop/stateWhitePop;
			double cdRatio = cdOtherPop/cdWhitePop;

			if(cdRatio<stateRatio)
				return cdRatio/stateRatio;
			else
				return stateRatio/cdRatio;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}, PARTISAN_FAIRNESS {
		@Override
		public double calculateGoodness(CongressionalDistrict district, State state) {
			return 0;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}
}
