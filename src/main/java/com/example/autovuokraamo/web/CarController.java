package com.example.autovuokraamo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

}
