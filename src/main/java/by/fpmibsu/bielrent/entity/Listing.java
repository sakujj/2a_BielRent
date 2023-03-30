package by.fpmibsu.bielrent.entity;


import java.util.List;

public class Listing implements Entity {

    private long id;
    private User user;
    private PropertyType propertyType;
    private Address address;
    private Filter filter;
    private String description;
    private List<Photo> photos;

    public Listing(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}
    public void setUser(User us){user = us;}

    public User getUser() {
        return user;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    public void setPropertyType(PropertyType pt){
        propertyType = pt;
    }
    public PropertyType getPropertyType(){
        return propertyType;
    }

}
