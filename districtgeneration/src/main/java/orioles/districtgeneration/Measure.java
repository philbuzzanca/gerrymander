package orioles.districtgeneration;

public interface Measure {
    public double calculateGoodness(State state);
    public double calculateGoodness(CongressionalDistrict district);
    public double normalize(double measure);
}
