package com.orioles.districtgeneration;

import com.orioles.model.Coordinate;

public class Edge {
    private Coordinate p1;
    private Coordinate p2;
    
    public Edge(Coordinate point1, Coordinate point2){
        this.p1 = point1;
        this.p2 = point2;
    }
    
    public Coordinate getP1(){
        return p1;
    }
    
    public Coordinate getP2(){
        return p2;
    }
    
    public void setP1(Coordinate point){
        this.p1 = point;
    }
    
    public void setP2(Coordinate point){
        this.p2 = point;
    }
    
    public boolean equals(Edge otherEdge){
        return this.p1.equals(otherEdge.p1) && this.p2.equals(otherEdge.p2)
                || this.p1.equals(otherEdge.p2) && this.p2.equals(otherEdge.p1);
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return this.equals((Edge) o);
	}
}
