package orioles.districtgeneration;

import java.util.List;

public class Compactness implements Measure{
    public double calculateGoodness(State state) {
        List<CongressionalDistrict> districts = state.getCongressionalDistricts();
        double sumGoodness = districts.stream().mapToDouble(this::calculateGoodness).sum();
        return sumGoodness/districts.size();
    }
    
    public double calculateGoodness(CongressionalDistrict district) {
        double area = district.calculateArea();
        double perimeter = district.calculatePerimeter();
        
        double r = Math.sqrt(area/Math.PI);
        double equalAreaPerimeter = 2 * Math.PI * r;

		return equalAreaPerimeter / perimeter;
    }

    public double normalize(double measure) {
        return 0;
    }
    
}
