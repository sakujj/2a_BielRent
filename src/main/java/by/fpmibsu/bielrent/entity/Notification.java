package by.fpmibsu.bielrent.entity;

public class Notification implements Entity{
    private long id;
    private CompositeFilter compositeFilter;

    public Notification() {}

    public CompositeFilter getCompositeFilter() {
        return compositeFilter;
    }

    public void setCompositeFilter(CompositeFilter compositeFilter) {
        this.compositeFilter = compositeFilter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
