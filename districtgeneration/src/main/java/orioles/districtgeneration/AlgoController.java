package orioles.districtgeneration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlgoController {
    
    
    @RequestMapping("/compactness")
    public Double compactnessTest(){
       CongressionalDistrict district = new CongressionalDistrict();
       Compactness measure = new Compactness();
       double goodness = measure.calculateGoodness(district);
       return goodness;
    }
}
