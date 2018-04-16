package orioles.districtgeneration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class CongressionalDistrict implements Cloneable {
    private List<Precinct> precincts;
    private String name;
    private double oldGoodness;
    
    public CongressionalDistrict(){
        precincts = new ArrayList<>();
        name = "";
        oldGoodness = 0;
    }
    
    public Stats summarize(){
        return null;
    }
    
    public Precinct choosePrecinct(){
        return null;
    }
    
    public Precinct getStartingPrecinct(){
        return null;
    }
    
    public Precinct removeFromDistrict(Precinct precinct){
        return precinct;
    }
    
    public void addToDistrict(Precinct precinct){
        precincts.add(precinct);
    }
    
    public Precinct getPrecinct(int precinctId){
        return null;
    }

    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOldGoodness() {
        return oldGoodness;
    }

    public void setOldGoodness(double oldGoodness) {
        this.oldGoodness = oldGoodness;
    }
    
    public int getNumPrecincts(){
    	return precincts.size();
    }
    
    public Precinct getRandomPrecinct(){
    	int random = (int)(Math.random()*precincts.size());
    	return precincts.get(random);
    }
    
    public double calculateArea(){
        return 100;                 //stubbed value for now
    }
    
    public double calculatePerimeter(){
        //return 50;
        double perimeter = 0;
        ArrayList<Edge> edges1 = new ArrayList<>();
        ArrayList<Edge> edges2 = new ArrayList<>();
        for(int i=0; i<precincts.size();i++){
            Precinct currentPrecinct = precincts.get(i);
            ArrayList<Point2D.Double> coordinates = currentPrecinct.getCoordinates();
            for(int j=0; j<coordinates.size()-1; j++){
                Point2D.Double p1 = coordinates.get(j);
                Point2D.Double p2 = coordinates.get(j+1);
                Edge edge = new Edge(p1, p2);
                Boolean found = false;
                for (Edge checkedge : edges1) {
                    if(checkedge.equals(edge))
                        found = true;
                }
                if(found==true){
                    System.out.println("here");
                    Boolean found2 = false;
                    for (Edge checkedge : edges2) {
                        if(checkedge.equals(edge))
                            found2 = true;
                    }
                    if(found2==false)
                        edges2.add(edge);
                }
                else{
                    edges1.add(edge);
                }
            }
        }
        for (Edge edge : edges2) {
            edges1.remove(edge);
        }
        for (Edge edge : edges1) {
            Point2D.Double point1 = edge.getP1();
            Point2D.Double point2 = edge.getP2();
            double distance = calculateDistance(point1, point2);
            perimeter+=distance;
         }
        
        return perimeter;
    }
    
    public double calculateDistance(Point2D.Double point1, Point2D.Double point2){
        //calculates distance useing haversine formula
        double radius = 6371.01;
        
        Point2D.Double p1 = new Point2D.Double(-77.860192445970085, 39.153000129599988);
        Point2D.Double p2 = new Point2D.Double(-77.862840901004034, 39.145148506742501);
        
        double latDistance = Math.toRadians(p2.getY() - p1.getY());
        double lonDistance = Math.toRadians(p2.getX() - p1.getX());
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(p1.getY())) * Math.cos(Math.toRadians(p2.getY()))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius* c;

        

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
        
}
