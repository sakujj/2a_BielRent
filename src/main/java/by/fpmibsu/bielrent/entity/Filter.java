package by.fpmibsu.bielrent.entity;

public abstract class Filter implements Entity {
    private long id;
    private int roomCount;
    private int floorCount;
    private int bedRoomCount;
    private double livingSpace;
    private double kitchenArea;
    private int price;
    private int balconyCount;

    private int year;
    private boolean hasSeparateBathroom;
    private boolean hasWashingMachine;
    private boolean hasFurniture;
    private boolean hasWiFi;
    private boolean hasElevator;



    public Filter() {}

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

    public int getBedRoomCount() {
        return bedRoomCount;
    }

    public void setBedRoomCount(int bedRoomCount) {
        this.bedRoomCount = bedRoomCount;
    }

    public double getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(double livingSpace) {
        this.livingSpace = livingSpace;
    }

    public double getKitchenArea() {
        return kitchenArea;
    }

    public void setKitchenArea(double kitchenArea) {
        this.kitchenArea = kitchenArea;
    }


    public int getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(int balconyCount) {
        this.balconyCount = balconyCount;
    }




    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isHasSeparateBathroom() {
        return hasSeparateBathroom;
    }

    public void setHasSeparateBathroom(boolean hasSeparateBathroom) {
        this.hasSeparateBathroom = hasSeparateBathroom;
    }

    public boolean isHasWashingMachine() {
        return hasWashingMachine;
    }

    public void setHasWashingMachine(boolean hasWashingMachine) {
        this.hasWashingMachine = hasWashingMachine;
    }

    public boolean isHasFurniture() {
        return hasFurniture;
    }

    public void setHasFurniture(boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
    }

    public boolean isHasWiFi() {
        return hasWiFi;
    }

    public void setHasWiFi(boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}