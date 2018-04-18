package com.orioles.districtgeneration;

import java.awt.geom.Point2D;

public class Edge {
    private Point2D.Double p1;
    private Point2D.Double p2;

    public Edge(Point2D.Double point1, Point2D.Double point2){
        this.p1 = point1;
        this.p2 = point2;
    }
    
    public Point2D.Double getP1(){
        return p1;
    }
    
    public Point2D.Double getP2(){
        return p2;
    }
    
    public void setP1(Point2D.Double point){
        this.p1 = point;
    }
    
    public void setP2(Point2D.Double point){
        this.p2 = point;
    }
    
    public boolean equals(Edge edge){
        if(getP1().equals(edge.getP1()) && getP2().equals(edge.getP2())){
            return true;
        } else return getP1().equals(edge.getP2()) && getP2().equals(edge.getP1());
    }
}
