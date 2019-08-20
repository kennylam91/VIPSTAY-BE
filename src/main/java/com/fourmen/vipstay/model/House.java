package com.fourmen.vipstay.model;

import javax.persistence.*;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseName;

    private String houseType;

    private String address;

    private Long bedRoomNumber;

    private Long bathroomNumber;

    @Column(columnDefinition = "long")
    private String description;

    private Long price;

    private String image;

    private Long rate;

    public House() {
    }

    public House(String houseName, String typeName, String address, Long bedRoomNumber, Long bathroomNumber, String description, Long price, String image, Long rate) {
        this.houseName = houseName;
        this.houseType = typeName;
        this.address = address;
        this.bedRoomNumber = bedRoomNumber;
        this.bathroomNumber = bathroomNumber;
        this.description = description;
        this.price = price;
        this.image = image;
        this.rate = rate;
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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBedRoomNumber() {
        return bedRoomNumber;
    }

    public void setBedRoomNumber(Long bedRoomNumber) {
        this.bedRoomNumber = bedRoomNumber;
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
}
