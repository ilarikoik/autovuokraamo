package com.example.autovuokraamo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

@RestController
public class CarRestController {

    @Autowired
    private CarRepository crepo;

    // kaikki autot
    @RequestMapping(value = { "/api/car" }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carRestListaaa() {
        List<Car> cars = (List<Car>) crepo.findAll();
        for (Car car : cars) {
            Vehicle vehicle = car.getVehicle();
            car.setVehicle(vehicle);
        }
        return cars;
    }

    // vuokratut
    @RequestMapping(value = { ("/api/rented") }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carRestList() {
        return (List<Car>) crepo.findByRented(true);
    }

    // vapaat
    @RequestMapping(value = { ("/api/notrented") }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carNotrentedRestList() {
        return (List<Car>) crepo.findByRented(false);
    }

}
