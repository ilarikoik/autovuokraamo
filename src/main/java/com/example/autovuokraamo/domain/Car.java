package com.example.autovuokraamo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// jos täällä ois yleistietoja jotka viittaa kaikkiin autoihin yleisesti niin vois käyttä extend
// mutta kun on vähä tarkempi ns. autokohtaiset tiedot niin viittaus car luokkaan riittää 
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long carId;

    @NotEmpty(message = "cant be empty")
    private String fuel, type;
    @Min(value = 1, message = "must be > 0")
    private int price, kilometers;
    @NotNull
    private Boolean rented = false; // eli on vapaa

    // car luokka viittaa vehicle luokkaan tässä joten extends ei tarvitse käyttää?

    /*
     * JSON IGNORE piti poistaa että tää lähettää vehicle tiedot json muotoo kans ??
     * Jos @JsonIgnore on merkitty vehicle-kenttään Car-luokassa, se tarkoittaa,
     * että tämä kenttä jätetään pois JSON-vastauksesta aina kun Car-olioita
     * muunnetaan JSON-muotoon.
     */

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
        return "[vehicleBrand =" + vehicle.getBrand() + "] --> carId=" + carId + ", fuel=" + fuel + ", type=" + type
                + ", price="
                + price
                + ", kilometers="
                + kilometers + ", rented=" + rented + "]";
    }

}
