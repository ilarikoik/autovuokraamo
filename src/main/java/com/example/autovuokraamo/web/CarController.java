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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.autovuokraamo.AutovuokraamotyoApplication;
import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarRepository crepo;

    @Autowired
    private VehicleRepository vrepo;
    @Autowired

    private BikeRepository brepo;

    @GetMapping("/cars")
    public String carList(Model model) {

        model.addAttribute("cars", crepo.findAll());

        // Hae autoja värin perusteella
        List<String> colors = new ArrayList<>();
        for (Car car : crepo.findAll()) {
            String color = car.getVehicle().getColor();
            if (!colors.contains(color)) {
                colors.add(color);
            }
        }
        model.addAttribute("colorlist", colors);

        // Hae autoja merkin perusteella
        List<String> brandit = new ArrayList<>();
        List<String> mopobrandit = new ArrayList<>();
        for (Bike s : brepo.findAll()) {
            String brandi = s.getVehicle().getBrand();
            mopobrandit.add(brandi);
        }
        for (Vehicle v : vrepo.findAll()) {
            String bra = v.getBrand();
            if (!brandit.contains(bra)) {
                if (!mopobrandit.contains(bra))
                    brandit.add(bra);
            }
        }
        model.addAttribute("brandit", brandit);

        // varatut autot lista /cars sivulle select option varte
        List<String> rentedbrandit = new ArrayList<>();
        for (Car car : crepo.findByRented(true)) {
            String merkki = car.getVehicle().getBrand();
            if (!rentedbrandit.contains(merkki)) {
                rentedbrandit.add(merkki);
            }
        }
        model.addAttribute("rentedbrandit", rentedbrandit);

        // vapaat autot lista /cars sivulle select option varte
        List<String> notrentedbrandit = new ArrayList<>();
        for (Car car : crepo.findByRented(false)) {
            String merkki = car.getVehicle().getBrand();
            if (!notrentedbrandit.contains(merkki)) {
                notrentedbrandit.add(merkki);
            }
        }
        model.addAttribute("notrentedbrandit", notrentedbrandit);

        return "carlist";
    }

    // lisätää auto
    @GetMapping("/addcar")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("otsikko", "Add Car");
        model.addAttribute("button", "Add Car");
        model.addAttribute("save", "/save");
        model.addAttribute("takaisin", "/cars");

        model.addAttribute("type", "car");

        return "car";
    }

    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute Car car, BindingResult br, Model model) {
        model.addAttribute("otsikko", "Add Car");
        model.addAttribute("button", "Add Car");
        model.addAttribute("save", "/save");

        if (br.hasErrors()) {
            return "car";
        }
        // pitää tallentaa vehicle ekaks muute errorrrr
        Vehicle ve = car.getVehicle();
        vrepo.save(ve);
        crepo.save(car);
        return "redirect:/cars";
    }

    // eri edit thymeleaffit ku muute menee iha solmuu
    @GetMapping("/editcar/{id}")
    public String editCar(@PathVariable("id") Long carId, Model model) {
        model.addAttribute("bike", crepo.findById(carId));
        model.addAttribute("otsikko", "Edit Car");
        model.addAttribute("button", "Updaaaate");
        model.addAttribute("takaisin", "/cars");
        model.addAttribute("save", "/save");

        return "car";
    }

    @PostMapping("/saverent/{id}")
    public String rentCar(@PathVariable("id") Long id, Model model) {
        Optional<Car> car = crepo.findById(id); // urlista saadaa id
        if (car.isPresent()) {
            Car carRented = car.get();
            boolean rented = carRented.getRented();
            // vaihdetaan päinvastaseen booleaniin klikillä
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

    // kaikki vuokratut autot
    @GetMapping("/rented")
    public String showRentedCars(Boolean rented, Model model) {
        model.addAttribute("takaisin", "/cars");

        List<Car> rentedList = crepo.findByRented(true);
        model.addAttribute("rentedlist", rentedList);
        return "rentedlist";

    }

    // autot merkin perusteella
    @GetMapping("/info/{brand}")
    public String findSameCars(@PathVariable("brand") String brand, Model model) {
        model.addAttribute("takaisin", "/cars");

        List<Car> carslist = new ArrayList<>();

        // ettii reposta merkin perusteella
        for (Vehicle vehicle : vrepo.findByBrand(brand)) {
            // jos se vehicle löytyy creposta ni tehää lista
            List<Car> cars = crepo.findByVehicle(vehicle);
            carslist.addAll(cars);
        }

        model.addAttribute("autotiedot", carslist);

        return "infos";
    }

    // kaikki vuokraamattomat autot
    @GetMapping("/notrented")
    public String showNotRentedCars(Boolean rented, Model model) {
        model.addAttribute("takaisin", "/cars");

        List<Car> notRentedList = crepo.findByRented(false);
        model.addAttribute("rentedlist", notRentedList);
        return "rentedlist";

    }

    // autot värin perusteella
    @GetMapping("/bycolor/{color}")
    public String showByColor(@PathVariable("color") String color, Model model) {
        model.addAttribute("takaisin", "/cars");

        Set<String> colors = new HashSet<>();
        for (Vehicle v : vrepo.findAll()) {
            String VeColor = v.getColor();
            if (VeColor.equalsIgnoreCase(color))
                colors.add(VeColor);
        }

        List<Car> byColor = new ArrayList<>();
        for (Car car : crepo.findAll()) {
            String c = car.getVehicle().getColor();
            if (c.equals(color)) {
                byColor.add(car);
            }
        }
        model.addAttribute("bycolor", byColor);
        return "bycolor";
    }

}
