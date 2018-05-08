package com.orioles.model;

import java.util.List;
import java.util.Map;

public class FeatureCollection {
    private String type;
    private List<Map> features;
    
    public FeatureCollection(){
        this.type = "FeatureCollection";
    }
    
    public FeatureCollection(List<Map> features){
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Map> getFeatures() {
        return features;
    }

    public void setFeatures(List<Map> features) {
        this.features = features;
    }
}