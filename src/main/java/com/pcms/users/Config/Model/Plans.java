package com.pcms.users.Config.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Plans {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int planId;

    private String planName;
    private double price;
    private String location;
    private String description;
    private LocalDate date;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Plans(int planId, String planName, double price, String location, String description,LocalDate date){
        this.planId =planId;
        this.planName =planName;
        this.price = price;
        this.location = location;
        this.description =description;
        this.date =date;
    }
    public Plans(){
        super();
    }


}
