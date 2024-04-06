package com.example.autovuokraamo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    // etitää reposta vehiclen perusteel joka annetaa parametrinä controllerissa
    // loopissa
    List<Car> findByVehicle(Vehicle vehicle);

    /* List<Car> findByVehicleList(List<Vehicle> list); */

    List<Car> findByRented(boolean b);

    /* List<Car> findByColor(String color); */

}
