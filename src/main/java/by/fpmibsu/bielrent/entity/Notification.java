package by.fpmibsu.bielrent.entity;

public class Notification {
    private Client client;
    private CompositeFilter compositeFilter;

    public Notification() {}

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
}
