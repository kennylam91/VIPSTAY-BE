package com.fourmen.vipstay.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseName;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @OneToMany(targetEntity = ImageOfHouse.class)
    private List<String> imageUrls;

    private String address;

    private Long bedroomNumber;

    private Long bathroomNumber;

    @Column(columnDefinition = "long")
    private String description;

    private Long price;

    private Long rate;

    private Long area;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;

    public House() {
    }

    public House(String houseName, Category category, String address, Long bedroomNumber, Long bathroomNumber, String description, Long price, Long rate, Long area, Status status, User user) {
        this.houseName = houseName;
        this.category = category;
        this.address = address;
        this.bedroomNumber = bedroomNumber;
        this.bathroomNumber = bathroomNumber;
        this.description = description;
        this.price = price;
        this.rate = rate;
        this.area = area;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Long bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Long getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Long bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }


    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
