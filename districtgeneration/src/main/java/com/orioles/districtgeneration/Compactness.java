package com.orioles.districtgeneration;


import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Measure;
import com.orioles.model.State;
import com.orioles.model.Stats;
import java.util.List;

public class Compactness implements Measure {
	@Override
    public double calculateGoodness(State state) {
        List<CongressionalDistrict> districts = state.getCongressionalDistricts();
        double sumGoodness = districts.stream().mapToDouble(this::calculateGoodness).sum();
        return sumGoodness/districts.size();
    }

        //need this for consistency with interface
	@Override
    public double calculateGoodness(CongressionalDistrict district, Stats stateStats) {
        double area = district.getArea();
        double perimeter = district.getPerimeter();
        
        double r = Math.sqrt(area/Math.PI);
        double equalAreaPerimeter = 2 * Math.PI * r;

		return equalAreaPerimeter / perimeter;
    }
    
    //this one gets used in total state calculation
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
}
