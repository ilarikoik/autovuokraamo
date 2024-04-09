package com.example.autovuokraamo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {

    List<Bike> findByRented(boolean b);

    List<Bike> findByVehicle(Vehicle vehicle);

    /* List<Bike> findByBikeBrand(String brand); */

}
