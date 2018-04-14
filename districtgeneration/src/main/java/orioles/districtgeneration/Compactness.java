package orioles.districtgeneration;

import java.util.List;

public class Compactness implements Measure{

    
    public double calculateGoodness(State state) {
        List<CongressionalDistrict> districts = state.getCongressionalDistricts();
        double[] districtGoodness = new double[state.getCongressionalDistricts().size()];
        double sumGoodness = 0;
        for(int i=0; i<districtGoodness.length; i++){
            sumGoodness+=calculateGoodness(districts.get(i));
        }
        sumGoodness = sumGoodness/districts.size();
        
        return sumGoodness;
    }
    
    public double calculateGoodness(CongressionalDistrict district) {
        
        double area = district.calculateArea();
        double perimeter = district.calculatePerimeter();
        
        double r = Math.sqrt(area/Math.PI);
        double equalAreaPerimeter = 2 * Math.PI * r;
        double score = 1 / (perimeter/equalAreaPerimeter);
        
        return score;
    }

    public double normalize(double measure) {
        return 0;
    }
    
}
