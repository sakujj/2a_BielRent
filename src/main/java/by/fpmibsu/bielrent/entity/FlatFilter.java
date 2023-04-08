package by.fpmibsu.bielrent.entity;

import java.sql.Date;

public class FlatFilter extends Filter{




    private int floorNumber;


    public FlatFilter() {}

    public FlatFilter(
            long id,
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
            int floorNumber) {
        super(id, roomCount, floorCount, bedroomCount, balconyCount, squareArea, priceMonthly, buildYear, hasBathroom, rentalPeriodStart, rentalPeriodEnd, hasWashingMachine, hasFurniture, hasWifi, hasElevator, listing);
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n FlatFilter{"
                + "\nfloorNumber: " +floorNumber
                + "}\n";
    }
}
