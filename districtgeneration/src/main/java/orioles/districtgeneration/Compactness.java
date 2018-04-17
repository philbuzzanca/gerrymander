package orioles.districtgeneration;

import orioles.model.Measure;
import orioles.model.State;
import orioles.model.CongressionalDistrict;

import java.util.List;

public class Compactness implements Measure {
	@Override
	public double calculateGoodness(State state) {
        List<CongressionalDistrict> districts = state.getCongressionalDistricts();
        double sumGoodness = districts.stream().mapToDouble(this::calculateGoodness).sum();
        return sumGoodness/districts.size();
    }

	@Override
    public double calculateGoodness(CongressionalDistrict district) {
        double area = district.getArea();
        double perimeter = district.getPerimeter();
        
        double r = Math.sqrt(area/Math.PI);
        double equalAreaPerimeter = 2 * Math.PI * r;

		return equalAreaPerimeter / perimeter;
    }

	public double normalize(double measure) {
        return 0;
    }
}
