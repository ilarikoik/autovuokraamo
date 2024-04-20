package com.example.autovuokraamo.web;

import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Controller
public class AdminBikeController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private BikeRepository brepo;
    @Autowired
    private VehicleRepository vrepo;

    // admin kaikki bikes tiedot
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = { ("/admin/bikes") })
    public String getBikes(Model model) {

        model.addAttribute("bikes", brepo.findAll());

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

        return "bikesadmin";
    }

    // varaa pyörä / true => !true
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adminrentbike/{id}")
    public String rentBike(@PathVariable("id") Long bikeId, Model model) {
        Optional<Bike> bike = brepo.findById(bikeId);
        if (bike.isPresent()) {
            Bike bikere = bike.get();
            Boolean isRented = bikere.isRented();
            bikere.setRented(!isRented);
            brepo.save(bikere);
        }

        return "redirect:/admin/bikes";

    }

    // eri thymeleaffit car ja bike muute iha solmussa
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addbike")
    public String addBike(Model model) {
        model.addAttribute("bike", new Bike());
        model.addAttribute("otsikko", "Add Bike");
        model.addAttribute("button", "Add Bike");
        model.addAttribute("save", "/savebike");
        model.addAttribute("takaisin", "/bikes");

        model.addAttribute("vehicletype", "Bike");

        return "bike";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletebike/{id}")
    public String deleteBike(@PathVariable("id") Long bikeId) {
        brepo.deleteById(bikeId);
        return "redirect:/admin/bikes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editbike/{id}")
    public String editBike(@PathVariable("id") Long bikeId, Model model) {
        model.addAttribute("bike", brepo.findById(bikeId));

        model.addAttribute("otsikko", "Edit Bike");
        model.addAttribute("button", "Update");
        model.addAttribute("takaisin", "/bikes");
        model.addAttribute("save", "/savebike");
        model.addAttribute("id", "bikdeId");
        return "bike";
    }

    @PostMapping("/savebike")
    public String saveBike(@Valid @ModelAttribute Bike bike, BindingResult br, Model model) {
        model.addAttribute("otsikko", "Edit Bike");
        model.addAttribute("button", "Update");
        model.addAttribute("vehicletype", "BBIKKE");

        if (br.hasErrors()) {
            return "bike";
        }

        // pitää tallentaa taas Vehicle eka
        Vehicle ve = bike.getVehicle();
        vrepo.save(ve);
        brepo.save(bike);

        return "redirect:/admin/bikes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resetallbikes")
    public String resetBikes(Model model) {
        List<Bike> rented = brepo.findByRented(true);
        for (Bike bike : rented) {
            bike.setRented(false);
        }
        brepo.saveAll(rented);
        return "redirect:/admin/bikes";
    }

}
