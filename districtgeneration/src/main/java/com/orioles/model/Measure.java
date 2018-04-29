package com.orioles.model;

public interface Measure {
    double calculateGoodness(State state);
    double calculateGoodness(CongressionalDistrict district, State state);
    double normalize(double measure);
}
