package com.example.autovuokraamo.web;

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
    public String editManufacturer(@PathVariable("id") Long carId, Model model) {
        model.addAttribute("car", crepo.findById(carId));
        return "editcar";
    }
    // ei toimi viel itse toiminto
    // tää nyt tekee uude objekti aina listaa eikä päivitä sitä mitä päivitetää
    /*
     * @GetMapping("/editcar/{id}")
     * public String editCar(@PathVariable("id") Long carId, Model model) {
     * Optional<Car> etsi = crepo.findById(carId);
     * 
     * if (etsi.isPresent()) {
     * model.addAttribute("car", etsi.get());
     * return "editcar";
     * } else {
     * return "carslist";
     * }
     */
    // muista edit linkkiin laittaa myös se /{id}(id=${car.carId})
    /* } */

}
