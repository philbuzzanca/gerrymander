package com.orioles.model;

public class Coordinate {
    
    private double x;
    private double y;
    
    public Coordinate(){
        x=0;
        y=0;
    }
    
    public Coordinate(double xVal, double yVal){
        this.x = xVal;
        this.y = yVal;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public void setX(double xValue){
        this.x = xValue;
    }
    
    public void setY(double yValue){
        this.y = yValue;
    }
}
