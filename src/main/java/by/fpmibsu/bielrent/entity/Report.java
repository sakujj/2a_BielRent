package by.fpmibsu.bielrent.entity;

public class Report implements Entity{
    private long id;
    private User user;
    private String description;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nReport{" +
                "id=" + id +
                ", userId=" + user +
                ", description='" + description + '\'' +
                "}\n";
    }
}
