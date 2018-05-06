/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orioles.helper_model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Demographics {

    @Id
    private String cdID;
    private String state;
    private int district;
    private int hispanic;
    private int white;
    private int black;
    private int nativeamerican;
    private int asian;
    private int pacificislander;
    private int other;
    private int multiple;
    private int population;
    
    public String getCdID() {
        return cdID;
    }

    public String getState() {
        return state;
    }

    public int getDistrict() {
        return district;
    }

    public int getHispanic() {
        return hispanic;
    }

    public int getWhite() {
        return white;
    }

    public int getBlack() {
        return black;
    }

    public int getNativeAmerican() {
        return nativeamerican;
    }

    public int getAsian() {
        return asian;
    }

    public int getPacificIslander() {
        return pacificislander;
    }

    public int getOther() {
        return other;
    }

    public int getMultiple() {
        return multiple;
    }

    public int getPopulation() {
        return population;
    }
}
