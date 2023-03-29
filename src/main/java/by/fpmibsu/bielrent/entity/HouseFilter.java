package by.fpmibsu.bielrent.entity;

public class HouseFilter extends Filter{
    private double landArea;
    private boolean hasOtherBuildings;

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public boolean getHasOtherBuildings() {
        return hasOtherBuildings;
    }

    public void setHasOtherBuildings(boolean hasOtherBuildings) {
        this.hasOtherBuildings = hasOtherBuildings;
    }
}
