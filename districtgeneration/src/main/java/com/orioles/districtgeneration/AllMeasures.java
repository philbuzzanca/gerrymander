package com.orioles.districtgeneration;

import com.orioles.model.CongressionalDistrict;

public enum AllMeasures implements Measure {
	COMPACTNESS {    // Schwartzberg
		@Override
		public double calculateGoodness(CongressionalDistrict district) {
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
		public double calculateGoodness(CongressionalDistrict district) {
			return district.summarize().getPopulation();
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}, PARTISAN_FAIRNESS {
		@Override
		public double calculateGoodness(CongressionalDistrict district) {
			return 0;
		}

		@Override
		public double normalize(double measure) {
			return 0;
		}
	}, RACIAL_FAIRNESS {
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
