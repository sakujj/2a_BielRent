package by.fpmibsu.bielrent.entity;

import java.util.List;

public class Address implements Entity{
    private long id;
    private int regionNumber;
    private String city;
    private String districtAdministrative = null;
    private String districtMicro = null;
    private String street;
    private int house;

    List<Listing> listingList;

    public void setId(long i){
        id = i;
    }
    public long getId(){
        return id;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String s){
        city = s;
    }
    public String getStreet(){
        return street;
    }
    public void setStreet(String s){
        street = s;
    }
    public int getHouse(){
        return house;
    }
    public void setHouse(int h){
        house = h;
    }


    public void setDistrictAdministrative(String s){
        districtAdministrative = s;
    }
    public String getDistrictAdministrative(){
        return districtAdministrative;
    }
    public void setRegionNumber(int s){
        regionNumber = s;
    }
    public int getRegionNumber(){
        return regionNumber;
    }
    public void setDistrictMicro(String s){
        districtMicro = s;
    }
    public String getDistrictMicro(){
        return districtMicro;
    }

    public List<Listing> getListings() {
        return listingList;
    }

    public void setListings(List<Listing> listingList) {
        this.listingList = listingList;
    }
}
