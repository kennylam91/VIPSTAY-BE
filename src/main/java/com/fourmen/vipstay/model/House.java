package com.fourmen.vipstay.model;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseName;

    @Ignore
    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    private String address;

    private Long bedroomNumber;

    private Long bathroomNumber;

    @Column(columnDefinition = "long")
    private String description;

    private Long price;

    private String image;

    private Long rate;

    private Long area;


    @Enumerated(EnumType.STRING)
    private StatusHouse status;



    @ManyToOne
    @JoinColumn(name = "owner")
    private User user;

//    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
//    private List<House_Guest> house_guests;

    public House() {
    }

    public House(String houseName, Category category , String address, Long bedroomNumber, Long bathroomNumber, String description, Long price, String image, Long rate, Long area) {
        this.houseName = houseName;
        this.category =category ;
        this.address = address;
        this.bedroomNumber = bedroomNumber;
        this.bathroomNumber = bathroomNumber;
        this.description = description;
        this.price = price;
        this.image = image;
        this.rate = rate;
        this.area = area;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusHouse getStatus() {
        return status;
    }

    public void setStatus(StatusHouse status) {
        this.status = status;
    }
}
