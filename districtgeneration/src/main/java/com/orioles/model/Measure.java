package com.orioles.model;

public interface Measure {
    double calculateGoodness(State state);
    double calculateGoodness(CongressionalDistrict district, Stats stateStats);
    double normalize(double measure);
}
