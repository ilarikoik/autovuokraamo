package com.example.autovuokraamo.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.autovuokraamo.AutovuokraamotyoApplication;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CarController {

    // liitä repo
    @Autowired
    private CarRepository crepo;

    @Autowired
    private VehicleRepository vrepo;

    @GetMapping("/cars")
    public String carList(Model model) {
        // vehicle tiedot löytyy myös creposta kun ne on tallennettu sinne Car olion
        // sisällä
        model.addAttribute("cars", crepo.findAll());

        // lähetetää kaikki värit valintoja varte
        List<String> colors = new ArrayList<>();
        for (Vehicle v : vrepo.findAll()) {
            String VehicleColor = v.getColor();
            if (!colors.contains(VehicleColor)) {
                colors.add(VehicleColor);
            }
        }
        model.addAttribute("colorlist", colors);

        // lähetetää kaikki merkit valintoja varte
        List<String> brandit = new ArrayList<>();
        for (Vehicle v : vrepo.findAll()) {
            String bra = v.getBrand();
            if (!brandit.contains(bra)) {
                brandit.add(bra);
            }
        }
        model.addAttribute("brandit", brandit);

        return "carlist";
    }

    @GetMapping("/addcar")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        return "addcar";
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car) {
        // pitää tallentaa taas Vehicle eka
        Vehicle ve = car.getVehicle();
        vrepo.save(ve);
        crepo.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/editcar/{id}")
    public String editCar(@PathVariable("id") Long carId, Model model) {
        model.addAttribute("car", crepo.findById(carId));
        return "editcar";
    }

    @PostMapping("/saverent/{id}")
    public String rentCar(@PathVariable("id") Long id, Model model) {
        Optional<Car> car = crepo.findById(id); // urlista saadaa id metodille
        if (car.isPresent()) {
            // jos löytyy haetaa sen tiedot ja vaihetaa rented kohta ja tallennetaa
            Car carRented = car.get();
            boolean rented = carRented.getRented();
            // vaihdetaan vastakkaiseen booleaniin klikillä
            carRented.setRented(!rented);
            crepo.save(carRented);
        } else {
            System.err.println("error");
        }
        return "redirect:/cars";
    }

    @GetMapping("/deletecar/{id}")
    public String deleteCar(@PathVariable("id") Long carId) {
        crepo.deleteById(carId);
        return "redirect:/cars";
    }

    @GetMapping("/rented")
    public String showRentedCars(Boolean rented, Model model) {
        List<Car> rentedList = crepo.findByRented(true);
        model.addAttribute("rentedlist", rentedList);
        return "rentedlist";

    }

    @GetMapping("/notrented")
    public String showNotRentedCars(Boolean rented, Model model) {
        List<Car> notRentedList = crepo.findByRented(false);
        model.addAttribute("notrentedlist", notRentedList);
        return "notrentedlist";

    }

    @GetMapping("/notrented/{brand}")
    public String showNotRentedCarsByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        List<Car> cars = crepo.findByRented(false);
        List<Car> notRentedList = new ArrayList<>();

        for (Car c : cars) {
            String currentCarBrand = c.getVehicle().getBrand();
            if (currentCarBrand.equals(brand)) {
                notRentedList.add(c);
            }
        }

        model.addAttribute("notrentedlist", notRentedList);
        return "notrentedbybrand";

    }

    @GetMapping("/rented/{brand}")
    public String showRentedCarsByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        List<Car> cars = crepo.findByRented(true);
        List<Car> rentedList = new ArrayList<>();

        for (Car c : cars) {
            String currentCarBrand = c.getVehicle().getBrand();
            if (currentCarBrand.equals(brand)) {
                rentedList.add(c);
            }
        }

        model.addAttribute("notrentedlist", rentedList);
        return "rentedbybrand";

    }

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    @GetMapping("/bycolor/{color}")
    public String showByColor(@PathVariable("color") String color, Model model) {

        // pitäs hakee eka sen merkin perustellaa creposta ja sitte haravoida sieltä
        // sama värilliset

        // ottaa urliin annetun color paramterin ja ettii sen nimiset värit Vehicle
        // listasta
        Set<String> colors = new HashSet<>();
        for (Vehicle v : vrepo.findAll()) {
            String VeColor = v.getColor();
            if (VeColor.equalsIgnoreCase(color))
                colors.add(VeColor);
        }

        List<Car> byColor = new ArrayList<>();
        for (Car car : crepo.findAll()) {
            // jokasta autoa kohden haetaan sen vehicle luokka ja sieltä väri
            String c = car.getVehicle().getColor();
            if (c.equals(color)) {
                byColor.add(car);
            }
        }

        // ehkä joteki tällei
        /* List<Car> lista = crepo.findByVehicle(vrepo.findByColor(color)); */

        model.addAttribute("autotiedot", byColor);
        return "bycolor";
    }

}
