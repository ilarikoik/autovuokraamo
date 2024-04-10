package com.example.autovuokraamo.domain;

import org.hibernate.annotations.NotFound;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bikeId;

    // einää validoinnit toimi missää luokassa?? menee läpi vaikka laitta null tai
    // ""
    // thynmeleaffi menee solmuu ku yrittää hakee tietoja jos laittaa null
    // mut ei mihikää tuu mitää
    // näit viestejä
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name must not be null")
    private String type, fuel;

    @Min(value = 1)
    private int price, kilometers;

    @NotNull
    private boolean rented;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    public Bike() {
    }

    public Bike(String type, String fuel, int price, int kilometers, boolean rented, Vehicle vehicle) {
        this.type = type;
        this.fuel = fuel;
        this.price = price;
        this.kilometers = kilometers;
        this.rented = rented;
        this.vehicle = vehicle;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Bike [bikeId=" + bikeId + ", type=" + type + ", fuel=" + fuel + ", price=" + price + ", kilometers="
                + kilometers + ", rented=" + rented + ", vehicle=" + vehicle + "]";
    }

}
