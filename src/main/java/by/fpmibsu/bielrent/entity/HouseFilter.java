package by.fpmibsu.bielrent.entity;

import java.sql.Date;

public class HouseFilter extends Filter{
    private double landArea;
    private boolean hasOtherBuildings;

    public HouseFilter() {
    }

    public HouseFilter(long id,
                       int roomCount,
                       int floorCount,
                       int bedroomCount,
                       int balconyCount,
                       double squareArea,
                       long priceMonthly,
                       int buildYear,
                       Date rentalPeriodStart,
                       Date rentalPeriodEnd,
                       boolean hasBathroom,
                       boolean hasWashingMachine,
                       boolean hasFurniture,
                       boolean hasWifi,
                       boolean hasElevator,
                       Listing listing,
                       double landArea,
                       boolean hasOtherBuildings) {
        super(id, roomCount, floorCount, bedroomCount, balconyCount, squareArea, priceMonthly, buildYear, hasBathroom, rentalPeriodStart, rentalPeriodEnd, hasWashingMachine, hasFurniture, hasWifi, hasElevator, listing);
        this.landArea = landArea;
        this.hasOtherBuildings = hasOtherBuildings;
    }

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

    @Override
    public String toString() {
        return super.toString() + "\nHouseFilter{" +
                "\nlandArea=" + landArea +
                "\n, hasOtherBuildings=" + hasOtherBuildings +
                "}\n";
    }
}
