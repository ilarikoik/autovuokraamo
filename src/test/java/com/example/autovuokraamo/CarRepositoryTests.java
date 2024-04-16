package com.example.autovuokraamo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CarRepositoryTests {

    @Autowired
    BikeRepository brepo;
    @Autowired
    VehicleRepository vrepo;
    @Autowired
    CarRepository crepo;

    // lista löytyy
    @Test
    public void listOverOne() {
        List<Car> list = (List<Car>) crepo.findAll();
        assertThat(list).hasSizeGreaterThan(0);
    }

    // eka auto on audi
    @Test
    public void firstCar() {
        List<Car> list = (List<Car>) crepo.findAll();
        assertThat(list).hasSizeGreaterThan(0);
        assertThat(list.get(0).getVehicle().getBrand())
                .isEqualTo("audi");
    }

    // lisäys
    /*
     * @Test
     * public void newCar() {
     * Vehicle vehicle = new Vehicle("testi", "testi", "liila");
     * vrepo.save(vehicle);
     * Car car = new Car("testi", "testi", 10, 20, false, vehicle);
     * crepo.save(car);
     * 
     * assertThat(crepo.findByType("testi")).isNotNull();
     * }
     */

    // poisto
    @Test
    public void createAndDeleeteNewCar() {
        Vehicle vehicle = new Vehicle("testi", "testi", "liila");
        vrepo.save(vehicle);
        Car car = new Car("testi", "testi", 10, 20, false, vehicle);
        crepo.save(car);

        Car auto = crepo.findByType("testi");

        crepo.delete(auto);
        assertThat(crepo.findByType("testi")).isNull();

    }

}
