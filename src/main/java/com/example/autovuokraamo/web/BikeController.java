package com.example.autovuokraamo.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BikeController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private BikeRepository brepo;
    @Autowired
    private VehicleRepository vrepo;

    // user bikes sivu
    @GetMapping("/bikes")
    public String bikesUser(Model model) {
        model.addAttribute("bikes", brepo.findByRented(false));

        // Hae mopoja värin perusteella
        List<String> bikecolors = new ArrayList<>();
        for (Bike bike : brepo.findAll()) {
            if (!bikecolors.contains(bike.getVehicle().getColor())) {
                bikecolors.add(bike.getVehicle().getColor());
            }
        }
        model.addAttribute("colors", bikecolors);

        List<String> brandilista = new ArrayList<>();
        for (Bike bike : brepo.findAll()) {
            String bikebrand = bike.getVehicle().getBrand();
            brandilista.add(bikebrand);
        }
        model.addAttribute("brandit", brandilista);

        // Vuokrattujen merkkien mukaan
        List<String> rentedbrandit = new ArrayList<>();
        for (Bike bike : brepo.findByRented(true)) {
            String merkki = bike.getVehicle().getBrand();
            if (!rentedbrandit.contains(merkki)) {
                rentedbrandit.add(merkki);
            }
        }
        model.addAttribute("rentedbrandit", rentedbrandit);

        // Hae vapaana olevien merkkin mukaan
        List<String> notrentedbrandit = new ArrayList<>();
        for (Bike car : brepo.findByRented(false)) {
            String merkki = car.getVehicle().getBrand();
            if (!notrentedbrandit.contains(merkki)) {
                notrentedbrandit.add(merkki);
            }
        }
        model.addAttribute("notrentedbrandit", notrentedbrandit);
        return "bikes";
    }

    // varaa pyörä / true => !true
    @PostMapping("/rentbike/{id}")
    public String rentBikeUser(@PathVariable("id") Long bikeId, Model model) {
        Optional<Bike> bike = brepo.findById(bikeId);
        if (bike.isPresent()) {
            Bike bikere = bike.get();
            Boolean isRented = bikere.isRented();
            bikere.setRented(!isRented);
            brepo.save(bikere);
        }
        return "redirect:/bikes";
    }

    // värin perusteella
    @GetMapping("/bikebycolor/{color}")
    public String showByColor(@PathVariable("color") String color, Model model) {
        model.addAttribute("takaisin", "/bikes");

        Set<String> colors = new HashSet<>();
        for (Vehicle v : vrepo.findAll()) {
            String VeColor = v.getColor();
            if (VeColor.equalsIgnoreCase(color))
                colors.add(VeColor);
        }

        List<Bike> byColor = new ArrayList<>();
        for (Bike Bike : brepo.findAll()) {
            String c = Bike.getVehicle().getColor();
            if (c.equals(color)) {
                byColor.add(Bike);
            }
        }
        model.addAttribute("bycolor", byColor);
        return "bycolor";
    }

    // kaikki vuokratut pyörät
    @GetMapping("/brented")
    public String showRentedBikes(Model model) {
        model.addAttribute("rentedlist", brepo.findByRented(true));
        model.addAttribute("takaisin", "/bikes");

        return "rentedlist";
    }

    // kaikki vuokraamattomat pyörät
    @GetMapping("/bnotrented")
    public String showNotRentedBikes(Model model) {
        model.addAttribute("takaisin", "/bikes");
        model.addAttribute("rentedlist", brepo.findByRented(false));
        return "rentedlist";
    }

    // merkin perusteella
    @GetMapping("/bikebybrand/{brand}")
    public String findBikes(@PathVariable("brand") String brand, Model model) {
        model.addAttribute("takaisin", "/bikes");

        List<Bike> mopot = new ArrayList<>();
        for (Bike bike : brepo.findAll()) {
            if (bike.getVehicle().getBrand().equals(brand)) {
                mopot.add(bike);
            }
        }
        model.addAttribute("autotiedot", mopot);

        log.info("\n \nkaikki mopot merkin mukaan");
        for (Bike bike : mopot) {
            System.out.println(bike.toString());
        }
        return "bybrand";
    }

    // vapaana olevat pyörät merkin perusteella
    @GetMapping("/bnotrented/{brand}")
    public String showNotRentedBikesByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        List<Bike> bikes = brepo.findByRented(false);
        List<Bike> notRentedList = new ArrayList<>();

        for (Bike b : bikes) {
            String currentCarBrand = b.getVehicle().getBrand();
            if (currentCarBrand.equals(brand)) {
                notRentedList.add(b);
            }
        }
        model.addAttribute("rentedlist", notRentedList);
        model.addAttribute("otsikko", "Not Rented by Brand");
        model.addAttribute("takaisin", "/bikes");

        return "rentedbybrand";

    }

    // varatut merkin perusteella vuokratut
    @GetMapping("/brented/{brand}")
    public String showRentedBikesByBrand(@PathVariable("brand") String brand, Boolean rented, Model model) {
        model.addAttribute("otsikko", "Rented by Brand");
        model.addAttribute("takaisin", "/bikes");

        List<Bike> rentedList = new ArrayList<>();
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
