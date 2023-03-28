package by.fpmibsu.bielrent.entity;


import java.util.List;

public class Listing implements Entity {
    private long id;
    private Category category;
    private RentalType rentalType;
    private Adress address;
    private CompositeFilter compositeFilter;
    private int price;

    private String description;
    private List<Photo> photos;

    public Listing(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }
    public Adress getAddress() {
        return address;
    }

    public void setAddress(Adress address) {
        this.address = address;
    }

    public CompositeFilter getCompositeFilter() {
        return compositeFilter;
    }

    public void setCompositeFilter(CompositeFilter compositeFilter) {
        this.compositeFilter = compositeFilter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
