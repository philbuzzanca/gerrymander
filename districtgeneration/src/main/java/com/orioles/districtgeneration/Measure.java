package com.orioles.districtgeneration;

import com.orioles.model.CongressionalDistrict;
import com.orioles.model.State;

public interface Measure {
    double calculateGoodness(State state);
    double calculateGoodness(CongressionalDistrict district);
    double normalize(double measure);
}
