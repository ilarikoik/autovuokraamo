package com.example.autovuokraamo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

@RestController
public class VehicleRestController {

    @Autowired
    VehicleRepository vrepo;

    // kaikki
    @GetMapping("/api/vehicles")
    public @ResponseBody List<Vehicle> kaikkiVehiclet() {
        return (List<Vehicle>) vrepo.findAll();
    }
}
