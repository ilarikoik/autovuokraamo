package com.example.autovuokraamo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

// automerkillä yhestä moneen suhde /*  */
// Yhteiset tiedot kaikille autoille.. merkki

@Entity
public class Vehicle {

    @Id // automaattinen ID luonti
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String brand, model, color;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, String color) {
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vehicle")
    private List<Car> car;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "id=" + id + ", brand=" + brand + ", model=" + model + ", color=" + color;
    }

}
