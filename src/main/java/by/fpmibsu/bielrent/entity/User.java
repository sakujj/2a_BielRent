package by.fpmibsu.bielrent.entity;

import java.math.BigDecimal;
import java.util.List;

public class User implements Entity{
    private long id;
    private String email;
    private String password;
    private String name;
    private Role role;
    private BigDecimal rating;
    private List<Report> reportList;
    private List<Notification> notificationList;
    private List<Listing> listingList;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }


    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Listing> getListings() {
        return listingList;
    }

    public List<Notification> getNotifications() {
        return notificationList;
    }

    public List<Report> getReports() {
        return reportList;
    }

    public void setListings(List<Listing> listingList) {
        this.listingList = listingList;
    }

    public void setNotifications(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void setReports(List<Report> reportList) {
        this.reportList = reportList;
    }
}
