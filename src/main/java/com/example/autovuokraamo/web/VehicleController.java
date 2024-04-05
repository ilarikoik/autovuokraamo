
package com.example.autovuokraamo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // se laittaa kaikki ku on eri värisii nii ei oo unique
    @GetMapping("/vehicles")
    public String vehicleList(Model model) {

        /* Iterable<Vehicle> audi = vrepo.findByBrand("audi"); */
        Iterable<Vehicle> audi = vrepo.findByBrand("audi");

        Iterable<Vehicle> kaikki = vrepo.findAll();
        Set<Vehicle> uniqueVehicles = new HashSet<>();
        for (Vehicle ve : kaikki) {
            uniqueVehicles.add(ve);
        }

        // uniikit
        Iterable<String> uniqueBrands = vrepo.findAllDistinctBrands();
        model.addAttribute("brands", uniqueBrands);

        model.addAttribute("vehicles", uniqueVehicles);
        model.addAttribute("audit", audi);
        return "vehiclelist";
    }

    @GetMapping("/info/{brand}")
    public String findSameCars(@PathVariable("brand") String brand, Model model) {
        // hakee vehicle reposta brandin mukaan
        Iterable<Vehicle> vehiclesByBrand = vrepo.findByBrand(brand);

        // lista autoille jotka osuu
        List<Car> carslist = new ArrayList<>();

        // käydää brandi lista läpi ja jos carReposta löytyy vehicle nii lisätää listaan
        for (Vehicle vehicle : vehiclesByBrand) {
            // tehää lista kaikista jotka osuu
            List<Car> cars = crepo.findByVehicle(vehicle); // metodi tehty carRepossa, sille annetaan parametrinä tällä
                                                           // hetkellä loopissa oleva Vehicle
                                                           // ja ettii reposta matcheja
                                                           // jos vehicle löytyy reposta nii lisätää koko
                                                           // auto objekti listaan
            carslist.addAll(cars);
        }

        model.addAttribute("autotiedot", carslist); // lähetetää tiedot
        return "infos";
    }
}
