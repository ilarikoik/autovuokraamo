package com.example.autovuokraamo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Car;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class BikeRestController {

    @Autowired
    private BikeRepository brepo;

    // kaikki pyörät
    @RequestMapping("/api/bike")
    public @ResponseBody List<Bike> bikeRest() {
        List<Bike> bikes = (List<Bike>) brepo.findAll();
        return (List<Bike>) bikes;
    }

    // vapaat
    @RequestMapping("/api/bnotrented")
    public @ResponseBody List<Bike> bikeRestNotRented() {
        List<Bike> bikes = (List<Bike>) brepo.findByRented(false);
        return (List<Bike>) bikes;
    }

    @RequestMapping("/api/brented")
    public @ResponseBody List<Bike> bikeRestRented() {
        List<Bike> bikes = (List<Bike>) brepo.findByRented(true);
        return (List<Bike>) bikes;
    }

}
