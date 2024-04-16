package com.example.autovuokraamo.domain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {

    List<Bike> findByRented(boolean b);

    List<Bike> findByVehicle(Vehicle vehicle);

    List<Bike> findByType(String string);

    Bike findOneByType(String type);

    Optional<Bike> findById(Long i);

    /* List<Bike> findByBrand(String brand); */

    /* List<Bike> findByBikeBrand(String brand); */

}
