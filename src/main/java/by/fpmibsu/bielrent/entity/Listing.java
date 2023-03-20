package by.fpmibsu.bielrent.entity;


import java.util.List;

public class Listing implements Entity {
    private long id;
    private CompositeFilter compositeFilter;
    private String address;
    private String description;
    private List<Photo> photos;

    public Listing(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompositeFilter getCompositeFilter() {
        return compositeFilter;
    }

    public void setCompositeFilter(CompositeFilter compositeFilter) {
        this.compositeFilter = compositeFilter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
