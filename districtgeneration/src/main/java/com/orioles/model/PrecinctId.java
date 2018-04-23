package com.orioles.model;

import java.io.Serializable;

public class PrecinctId implements Serializable {
    private int id; // Precinct's code (internal to state)
    private String state;
    private int cd;
    public PrecinctId(){
    }
    
    public PrecinctId(int id, String state, int cd){
        this.id = id;
        this.state = state;
        this.cd = cd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }
    
    
}
