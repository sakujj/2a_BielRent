package by.fpmibsu.bielrent.entity;

import java.util.List;

public class Address implements Entity{
    private long id;
    private int regionNumber;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private int houseNumber;
   List<Listing> listings;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRegionNumber() {
        return regionNumber;
    }

    public void setRegionNumber(int regionNumber) {
        this.regionNumber = regionNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrictAdministrative() {
        return districtAdministrative;
    }

    public void setDistrictAdministrative(String districtAdministrative) {
        this.districtAdministrative = districtAdministrative;
    }

    public String getDistrictMicro() {
        return districtMicro;
    }

    public void setDistrictMicro(String districtMicro) {
        this.districtMicro = districtMicro;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", regionNumber=" + regionNumber +
                ", city='" + city + '\'' +
                ", districtAdministrative='" + districtAdministrative + '\'' +
                ", districtMicro='" + districtMicro + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                "}\n";
    }
}
