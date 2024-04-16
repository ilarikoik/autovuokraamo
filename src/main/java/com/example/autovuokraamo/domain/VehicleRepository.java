package com.example.autovuokraamo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> findByBrand(String brand);

    @Query("SELECT DISTINCT v.brand FROM Vehicle v")
    Iterable<String> findAllDistinctBrands();

    List<Vehicle> findByColor(String color);

    Vehicle findOneByBrand(String string);

    /* List<Vehicle> findByType(String string); */
}
