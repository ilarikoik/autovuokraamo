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
import org.springframework.web.bind.annotation.PostMapping;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

import jakarta.validation.Valid;

@Controller
public class BikeController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private BikeRepository brepo;
    @Autowired
    private VehicleRepository vrepo;

    @GetMapping(value = { ("/bikes") })
    public String getBikes(Model model) {
        model.addAttribute("bikes", brepo.findAll());

        // haetaa värin perusteella
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
        model.addAttribute("brandit", brandilista); // lähetetää tiedot

        return "bikes";
    }

    @GetMapping("/addbike")
    public String addCar(Model model) {
        model.addAttribute("bike", new Bike());
        return "addbike";
    }

    @GetMapping("/deletebike/{id}")
    public String deleteBike(@PathVariable("id") Long bikeId) {
        brepo.deleteById(bikeId);
        return "redirect:/bikes";
    }

    @GetMapping("/editbike/{id}")
    public String editbike(@PathVariable("id") Long bikeId, Model model) {
        model.addAttribute("bike", brepo.findById(bikeId));
        return "editbike";
    }

    @PostMapping("/savebike")
    public String saveCar(@Valid @ModelAttribute Bike bike, BindingResult br) {

        // jos ei validointi ei mee läpi nii palauttaa toiselle error sivulle, lisää
        // error tekstit viel
        if (br.hasErrors()) {
            return "redirect:/Error";
        }
        // pitää tallentaa taas Vehicle eka
        Vehicle ve = bike.getVehicle();
        vrepo.save(ve);
        brepo.save(bike);
        return "redirect:/bikes";
    }

    @PostMapping("/rentbike/{id}")
    public String rentBike(@PathVariable("id") Long bikeId, Model model) {
        Optional<Bike> bike = brepo.findById(bikeId);
        if (bike.isPresent()) {
            Bike bikere = bike.get(); // otetaa se olio
            Boolean isRented = bikere.isRented(); // boolean arvo
            bikere.setRented(!isRented); // käännetää toisteppäi se arvo
            brepo.save(bikere); // talletus
        }
        return "redirect:/bikes";

    }

    @GetMapping("/bikebycolor/{color}")
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

        List<Bike> byColor = new ArrayList<>();
        for (Bike Bike : brepo.findAll()) {
            // jokasta mopoa kohden haetaan sen vehicle luokka ja sieltä väri
            String c = Bike.getVehicle().getColor();
            if (c.equals(color)) {
                byColor.add(Bike);
            }
        }

        model.addAttribute("bikesbycolor", byColor);
        return "bikebycolor";
    }

    @GetMapping("/brented")
    public String showRentedBikesByBrand(Model model) {
        model.addAttribute("rentedlist", brepo.findByRented(true));
        return "rentedlist";
    }

    @GetMapping("/bnotrented")
    public String showNotRentedBikesByBrand(Model model) {
        model.addAttribute("notrentedlist", brepo.findByRented(false));
        return "notrentedlist";
    }

    @GetMapping("/bikeinfo/{brand}")
    public String findBikes(@PathVariable("brand") String brand, Model model) {

        List<Bike> mopot = new ArrayList<>();
        for (Bike bike : brepo.findAll()) {
            if (bike.getVehicle().getBrand().equals(brand)) {
                mopot.add(bike);
            }
        }
        model.addAttribute("autotiedot", mopot); // lähetetää tiedot

        // ei voi kutsua suoraan niinku main metodissa vaa pitää url kautta kutsua
        log.info("\n \nkaikki mopot merkin mukaan");
        for (Bike bike : mopot) {
            System.out.println(bike.toString());
        }

        return "infos";
    }
}
