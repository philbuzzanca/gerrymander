
package com.orioles.districtgeneration;

import com.orioles.constants.Race;
import static com.orioles.constants.Race.WHITE;
import com.orioles.model.CongressionalDistrict;
import com.orioles.model.Measure;
import com.orioles.model.State;
import java.util.HashMap;
import java.util.Set;


public class DemographicMeasure implements Measure{

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
        HashMap<Race, Long> cdDemographics = (HashMap)district.getStats().getRaces();
        HashMap<Race, Long> stateDemographics = (HashMap)state.getStats().getRaces();
        
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
    
}
