package by.fpmibsu.bielrent.entity;

public class CompositeFilter implements Entity {
    private long id;
    private int roomCount;
    private int floorCount;
    private int bedRoomCount;
    private double livingSpace;
    private double kitchenArea;
    private double landArea;
    private int floor;
    private int floorsOfHouse;
    private String balcony;
    private String renovation;
    private String typeOfHouse;
    private int year;
    private boolean hasSeparateBathroom;
    private boolean hasWashingMachine;
    private boolean hasFurniture;
    private boolean hasWiFi;
    private boolean hasElevator;
    private boolean hasOtherBuildings;

    public CompositeFilter() {}

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

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloorsOfHouse() {
        return floorsOfHouse;
    }

    public void setFloorsOfHouse(int floorsOfHouse) {
        this.floorsOfHouse = floorsOfHouse;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getRenovation() {
        return renovation;
    }

    public void setRenovation(String renovation) {
        this.renovation = renovation;
    }

    public String getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(String typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
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

    public boolean isHasOtherBuildings() {
        return hasOtherBuildings;
    }

    public void setHasOtherBuildings(boolean hasOtherBuildings) {
        this.hasOtherBuildings = hasOtherBuildings;
    }
}