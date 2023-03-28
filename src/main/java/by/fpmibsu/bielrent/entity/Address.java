package by.fpmibsu.bielrent.entity;

public class Address implements Entity{
    private long id;
    private String region;
    private String city;
    private String area = null;
    private String street;
    private int house;
    private Integer flat = null;

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
    public Integer getFlat(){
        return flat;
    }
    public void setFlat(Integer h){
        flat = h;
    }
    public void setArea(String s){
        area = s;
    }
    public String getArea(){
        return area;
    }
    public void setRegion(String s){
        region = s;
    }
    public String getRegion(){
        return region;
    }
}
