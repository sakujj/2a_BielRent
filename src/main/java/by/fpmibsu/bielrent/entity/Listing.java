package by.fpmibsu.bielrent.entity;

public class Listing {
    private long id;
    private Client client;
    private CompositeFilter compositeFilter;
    private String address;
    private String description;

    public Listing(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
}
