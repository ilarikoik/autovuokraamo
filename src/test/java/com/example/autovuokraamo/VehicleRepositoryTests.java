
package com.example.autovuokraamo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import jakarta.transaction.Transactional;

@SpringBootTest
public class VehicleRepositoryTests {

    @Autowired
    VehicleRepository vrepo;

    // ei oo tyhjä
    @Test
    public void listHigherThanZero() {
        List<Vehicle> vehicles = (List<Vehicle>) vrepo.findAll();
        assertThat(vehicles).isNotNull();
    }

    // eka pyörä on Honda

    @Test
    public void findByType() {
        List<Vehicle> vehicles = (List<Vehicle>) vrepo.findAll();
        assertThat(vehicles).hasSizeGreaterThan(0);
        assertThat(vehicles.get(0).getBrand()).isEqualTo("audi");
    }

    // kaikki osat löytyy

    @Test
    public void findAllAttributes() {
        List<Vehicle> vehicles = (List<Vehicle>) vrepo.findAll();
        assertThat(vehicles).hasSizeGreaterThan(0);
        assertThat(vehicles)
                // siistarkstaa että Vehicle oliolla on VehicleId ?
                .extracting(Vehicle::getBrand)
                // ja sitten se ottaa listan ensimmäisen ja katsoo että siltä löytyy
                .contains(vehicles.get(0).getBrand());
        assertThat(vehicles)
                .extracting(Vehicle::getModel)
                .contains(vehicles.get(0).getModel());
        assertThat(vehicles)
                .extracting(Vehicle::getColor)
                .contains(vehicles.get(0).getColor());

    }

    // uus pyörä

    /*
     * @Test
     * public void createVehicle() {
     * Vehicle vehicle = new Vehicle("testi", "testi", "liila");
     * vrepo.save(vehicle);
     * Vehicle testVehicle = vrepo.findOneByBrand("testi");
     * 
     * assertThat(testVehicle.getModel())
     * .isEqualTo("testi");
     * assertThat(testVehicle.getColor())
     * .isEqualTo("liila");
     * }
     */

    // luonti ja poisto
    @Test
    public void deleteVehicle() {
        Vehicle vehicle = new Vehicle("toimiiko", "testi", "liila");
        vrepo.save(vehicle);
        Vehicle Vehiclee = vrepo.findOneByBrand("toimiiko");
        vrepo.delete(Vehiclee);

        assertThat(vrepo.findOneByBrand("toimiiko"))
                .isNull();

    }

}