package com.example.autovuokraamo.domain;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// jos täällä ois yleistietoja jotka viittaa kaikkiin autoihin yleisesti niin vois käyttä extend
// mutta kun on vähä tarkempi ns. autokohtaiset tiedot niin viittaus car luokkaan riittää 
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long carId;
    private String fuel, type;
    private int price, kilometers;
    private Boolean rented = false; // eli on vapaa

    // tuotteella yksi valmistaja
    // car luokka viittaa vehicle luokkaan tässä joten extends ei tarvitse käyttää?
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    public Car() {
    }

    public Car(String fuel, String type, int price, int kilometers, boolean rented, Vehicle vehicle) {
        super();
        this.fuel = fuel;
        this.type = type;
        this.price = price;
        this.kilometers = kilometers;
        this.rented = rented;
        this.vehicle = vehicle;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
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

    public Boolean getRented() {
        return rented;
    }

    public void setRented(Boolean rented) {
        this.rented = rented;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[vehicleID=" + vehicle.getId() + "] --> carId=" + carId + ", fuel=" + fuel + ", type=" + type
                + ", price="
                + price
                + ", kilometers="
                + kilometers + ", rented=" + rented + "]";
    }

}
