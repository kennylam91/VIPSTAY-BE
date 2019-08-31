package com.fourmen.vipstay.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class StatusHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private  House house;

    @Column(columnDefinition = "date")
    private Date startDate;

    @Column(columnDefinition = "date")
    private Date endDate;

    public StatusHouse() {
    }

    public StatusHouse(House house, Date startDate, Date endDate) {
        this.house = house;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
