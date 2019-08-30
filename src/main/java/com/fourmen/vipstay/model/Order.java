package com.fourmen.vipstay.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date checkIn;

    private Date checkOut;

    @Enumerated(EnumType.STRING)
    private StatusHouse status;

    @ManyToOne
    @JoinColumn
    private House house;

    @ManyToOne
    @JoinColumn
    private User user;

    public Order() {
    }

    public Order(Date startDate, Date endDate, Date checkIn, Date checkOut, StatusHouse status, House house, User user) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.house = house;
        this.user = user;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public StatusHouse getStatus() {
        return status;
    }

    public void setStatus(StatusHouse status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
