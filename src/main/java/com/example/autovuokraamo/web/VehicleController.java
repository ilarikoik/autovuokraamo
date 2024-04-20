
package com.example.autovuokraamo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

@Controller
public class VehicleController {

    @Autowired
    VehicleRepository vrepo;

    @Autowired
    CarRepository crepo;

    @Autowired
    BikeRepository brepo;

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @GetMapping("/")
    public String vehicleList(Model model) {

        Iterable<String> uniqueBrands = vrepo.findAllDistinctBrands();
        model.addAttribute("brands", uniqueBrands);
        return "vehiclelist";
    }

    @GetMapping("/allvehiclesby/{brand}")
    public String findAllVehiclesByBrand(@PathVariable("brand") String brand, Model model) {

        List<Vehicle> kaikki = vrepo.findByBrand(brand);

        // sekalista autoille ja pyörille
        List<Object> a = new ArrayList<>();

        for (Vehicle vehicle : kaikki) {
            // haetaa reposta kaikki ne autot/pyörät jotka matchaa brandiin
            List<Bike> bikes = brepo.findByVehicle(vehicle);
            List<Car> cars = crepo.findByVehicle(vehicle);
            a.addAll(bikes);
            a.addAll(cars);
        }
        model.addAttribute("autotiedot", a);

        log.info("\n \nkaikki tuotteet merkin mukaan");
        for (Object aa : a) {
            System.out.println(aa.toString());
        }

        return "bybrand";
    }

    // merkin perusteella vapaana olevat vehiclet
    @GetMapping("/notrented/{brand}")
    public String showNotRentedVehiclesByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        model.addAttribute("takaisin", "/cars");

        List<Car> cars = crepo.findByRented(false);
        List<Object> notRentedList = new ArrayList<>();

        for (Car c : cars) {
            String currentCarBrand = c.getVehicle().getBrand();
            if (currentCarBrand.equals(brand)) {
                notRentedList.add(c);
            }
        }
        for (Bike c : brepo.findByRented(false)) {
            String currentBikeBrand = c.getVehicle().getBrand();
            if (currentBikeBrand.equals(brand)) {
                notRentedList.add(c);
            }
        }
        model.addAttribute("rentedlist", notRentedList);
        return "rentedbybrand";
    }

    // merkin perusteella vuokratut vehiclet
    @GetMapping("/rented/{brand}")
    public String showRentedVehiclesByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        model.addAttribute("takaisin", "/cars");

        List<Car> cars = crepo.findByRented(true);
        List<Object> rentedList = new ArrayList<>();

        for (Car c : cars) {
            String currentCarBrand = c.getVehicle().getBrand();
            if (currentCarBrand.equals(brand)) {
                rentedList.add(c);
            }
        }
        for (Bike c : brepo.findByRented(true)) {
            String currentBikeBrand = c.getVehicle().getBrand();
            if (currentBikeBrand.equals(brand)) {
                rentedList.add(c);
            }
        }
        model.addAttribute("rentedlist", rentedList);
        return "rentedbybrand";
    }
}
