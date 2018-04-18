package com.orioles.districtgeneration;

import com.orioles.model.CongressionalDistrict;
import com.orioles.model.State;

import java.util.List;
import java.util.OptionalDouble;

public enum AllMeasures implements Measure{
	COMPACTNESS {	// Schwartzberg
		@Override
		public double calculateGoodness(State state) {
			List<CongressionalDistrict> districts = state.getCongressionalDistricts();
			OptionalDouble avgGoodness = districts.stream().mapToDouble(this::calculateGoodness).average();
			return avgGoodness.isPresent() ? avgGoodness.getAsDouble() : -1;
		}

		@Override
		public double calculateGoodness(CongressionalDistrict district) {
			double area = district.getArea();
			double perimeter = district.getPerimeter();

			double r = Math.sqrt(area/Math.PI);
			double equalAreaPerimeter = 2 * Math.PI * r;
			return equalAreaPerimeter / perimeter;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	},
	EQUAL_POPULATION {
		@Override
		public double calculateGoodness(State state) {
			return 0;
		}

		@Override
		public double calculateGoodness(CongressionalDistrict district) {
			return 0;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	},
	PARTISAN_FAIRNESS {
		@Override
		public double calculateGoodness(State state) {
			return 0;
		}

		@Override
		public double calculateGoodness(CongressionalDistrict district) {
			return 0;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}
}
