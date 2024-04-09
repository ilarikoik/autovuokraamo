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

    @Autowired
    private VehicleRepository vrepo;

    /*
     * @RequestMapping(value = { ("/carsrest") }, method = RequestMethod.GET)
     * public @ResponseBody List<Vehicle> vehicleRestList() {
     * return (List<Vehicle>) vrepo.findAll();
     * }
     */

    /*
     * @RequestMapping(value = { "/car" }, method = RequestMethod.GET)
     * public @ResponseBody List<Car> carRestListaaaa() {
     * List<Car> cars = (List<Car>) crepo.findAll();
     * List<Vehicle> ve = new ArrayList<>();
     * for (Car car : cars) {
     * Vehicle vehicle = car.getVehicle();
     * ve.add(vehicle);
     * }
     * 
     * List<Car> vittu = new ArrayList<>();
     * for (Car car : cars) {
     * Car nCar = new Car(car.getFuel(), car.getType(), car.getPrice(),
     * car.getKilometers(), car.getRented(),
     * car.getVehicle());
     * vittu.add(nCar);
     * }
     * 
     * return vittu;
     * 
     * }
     */

    @RequestMapping(value = { "/api/car" }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carRestListaaa() {
        List<Car> cars = (List<Car>) crepo.findAll();

        for (Car car : cars) {
            Vehicle vehicle = car.getVehicle();
            car.setVehicle(vehicle);
        }

        return cars;
    }

    @RequestMapping(value = { ("/api/rented") }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carRestList() {
        return (List<Car>) crepo.findByRented(true);
    }

    @RequestMapping(value = { ("/api/notrented") }, method = RequestMethod.GET)
    public @ResponseBody List<Car> carNotrentedRestList() {
        return (List<Car>) crepo.findByRented(false);
    }

}
