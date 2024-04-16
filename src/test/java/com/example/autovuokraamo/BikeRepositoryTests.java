package com.example.autovuokraamo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BikeRepositoryTests {

    @Autowired
    BikeRepository brepo;
    @Autowired
    VehicleRepository vrepo;

    // ei oo tyhjä
    @Test
    public void listHigherThanZero() {
        List<Bike> bikes = (List<Bike>) brepo.findAll();
        assertThat(bikes).hasSizeGreaterThan(0);
    }

    // eka pyörä on Honda
    @Test
    public void findByType() {
        List<Bike> bikes = (List<Bike>) brepo.findAll();
        assertThat(bikes).hasSizeGreaterThan(0);
        assertThat(bikes.get(0).getVehicle().getBrand()).isEqualTo("Honda");
    }

    // kaikki osat löytyy
    @Test
    public void findAllAttributes() {
        List<Bike> bikes = (List<Bike>) brepo.findAll();
        assertThat(bikes).hasSizeGreaterThan(0);
        assertThat(bikes)
                // siistarkstaa että Bike oliolla on bikeId ?
                .extracting(Bike::getBikeId)
                // ja sitten se ottaa listan ensimmäisen ja katsoo että siltä löytyy bikeId?
                .contains(bikes.get(0).getBikeId());
        assertThat(bikes)
                .extracting(Bike::getFuel)
                .contains(bikes.get(0).getFuel());
        assertThat(bikes)
                .extracting(Bike::getType)
                .contains(bikes.get(0).getType());
        assertThat(bikes)
                .extracting(Bike::getKilometers)
                .contains(bikes.get(0).getKilometers());
        assertThat(bikes)
                .extracting(Bike::getPrice)
                .contains(bikes.get(0).getPrice());
        assertThat(bikes)
                .extracting(Bike::getVehicle)
                .contains(bikes.get(0).getVehicle());
    }

    // uus pyörä
    /*
     * @Test
     * public void createBike() {
     * Vehicle vehicle = new Vehicle("testi", "testi", "liila");
     * vrepo.save(vehicle);
     * Bike bike = new Bike("testi", "testi", 100, 200, false, vehicle);
     * brepo.save(bike);
     * Bike testbike = brepo.findOneByType("testi");
     * 
     * assertThat(testbike.getType())
     * .isEqualTo("testi");
     * assertThat(testbike.getVehicle().getColor())
     * .isEqualTo("liila");
     * }
     */

    // poisto
    @Test
    public void createAndDeleteBike() {
        Vehicle vehicle = new Vehicle("testi", "testi", "liila");
        vrepo.save(vehicle);
        Bike bike = new Bike("testi", "testi", 100, 200, false, vehicle);
        brepo.save(bike);
        Bike bikee = brepo.findOneByType("testi");
        brepo.delete(bikee);

        assertThat(brepo.findByType("testi").isEmpty());

    }

}
