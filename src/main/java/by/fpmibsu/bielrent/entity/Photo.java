package by.fpmibsu.bielrent.entity;

public class Photo implements Entity{
    private long id;
    private String path;
    private Listing listing;

    public Photo(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public void setListing(Listing list){
        listing = list;
    }
    public Listing getListing(){
        return listing;
    }
}
