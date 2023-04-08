package by.fpmibsu.bielrent.entity;

import java.sql.Date;
import java.util.List;

public abstract class Filter implements Entity {
    private long id;
    private int roomCount;
    private int floorCount;
    private int bedroomCount;
    private int balconyCount;
    private double squareArea;
    private long priceMonthly;

    private int buildYear;
    private boolean hasBathroom;
    private Date rentalPeriodStart;
    private Date rentalPeriodEnd;
    private boolean hasWashingMachine;
    private boolean hasFurniture;
    private boolean hasWifi;
    private boolean hasElevator;
    private Listing listing = null;

    public Filter() {}

    public Filter(long id, int roomCount, int floorCount, int bedroomCount, int balconyCount, double squareArea, long priceMonthly, int buildYear, boolean hasBathroom, Date rentalPeriodStart, Date rentalPeriodEnd, boolean hasWashingMachine, boolean hasFurniture, boolean hasWifi, boolean hasElevator, Listing listing) {
        this.id = id;
        this.roomCount = roomCount;
        this.floorCount = floorCount;
        this.bedroomCount = bedroomCount;
        this.balconyCount = balconyCount;
        this.squareArea = squareArea;
        this.priceMonthly = priceMonthly;
        this.buildYear = buildYear;
        this.hasBathroom = hasBathroom;
        this.rentalPeriodStart = rentalPeriodStart;
        this.rentalPeriodEnd = rentalPeriodEnd;
        this.hasWashingMachine = hasWashingMachine;
        this.hasFurniture = hasFurniture;
        this.hasWifi = hasWifi;
        this.hasElevator = hasElevator;
        this.listing = listing;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }

    public int getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public int getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(int balconyCount) {
        this.balconyCount = balconyCount;
    }

    public double getSquareArea() {
        return squareArea;
    }

    public void setSquareArea(double squareArea) {
        this.squareArea = squareArea;
    }

    public long getPriceMonthly() {
        return priceMonthly;
    }

    public void setPriceMonthly(long priceMonthly) {
        this.priceMonthly = priceMonthly;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public boolean getHasBathroom() {
        return hasBathroom;
    }

    public void setHasBathroom(boolean hasBathroom) {
        this.hasBathroom = hasBathroom;
    }

    public Date getRentalPeriodStart() {
        return rentalPeriodStart;
    }

    public void setRentalPeriodStart(Date rentalPeriodStart) {
        this.rentalPeriodStart = rentalPeriodStart;
    }

    public Date getRentalPeriodEnd() {
        return rentalPeriodEnd;
    }

    public void setRentalPeriodEnd(Date rentalPeriodEnd) {
        this.rentalPeriodEnd = rentalPeriodEnd;
    }

    public boolean getHasWashingMachine() {
        return hasWashingMachine;
    }

    public void setHasWashingMachine(boolean hasWashingMachine) {
        this.hasWashingMachine = hasWashingMachine;
    }

    public boolean getHasFurniture() {
        return hasFurniture;
    }

    public void setHasFurniture(boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
    }

    public boolean getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean getHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }
    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "\nFilter{" +
                "id=" + id +
                ",\n roomCount=" + roomCount +
                ",\n floorCount=" + floorCount +
                ",\n bedroomCount=" + bedroomCount +
                ",\n balconyCount=" + balconyCount +
                ",\n squareArea=" + squareArea +
                ",\n priceMonthly=" + priceMonthly +
                ",\n buildYear=" + buildYear +
                ",\n hasBathroom=" + hasBathroom +
                ",\n rentalPeriodStart=" + rentalPeriodStart +
                ",\n rentalPeriodEnd=" + rentalPeriodEnd +
                ",\n hasWashingMachine=" + hasWashingMachine +
                ",\n hasFurniture=" + hasFurniture +
                ",\n hasWifi=" + hasWifi +
                ",\n hasElevator=" + hasElevator +
                ",\n listingId=" + listing +
                "}\n";
    }
}