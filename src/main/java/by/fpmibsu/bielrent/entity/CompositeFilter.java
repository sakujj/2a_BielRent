package by.fpmibsu.bielrent.entity;

public class CompositeFilter {
    private long id;
    private String realEstateType;
    private String price;
    private int roomCount;
    private int floorCount;
    private int bedroomCount;
    private boolean hasSeparateBathroom;
    private int floor;

    public CompositeFilter(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public boolean isHasSeparateBathroom() {
        return hasSeparateBathroom;
    }

    public void setHasSeparateBathroom(boolean hasSeparateBathroom) {
        this.hasSeparateBathroom = hasSeparateBathroom;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
