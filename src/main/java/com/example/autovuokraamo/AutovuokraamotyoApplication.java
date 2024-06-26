package com.example.autovuokraamo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.domain.BikeRepository;
import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class AutovuokraamotyoApplication {

	private static final Logger log = LoggerFactory.getLogger(AutovuokraamotyoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AutovuokraamotyoApplication.class, args);
	}

	// bean commanline runner tallenna repoo pari testiä
	@Bean
	public CommandLineRunner carRental(CarRepository crepo, VehicleRepository vehirepo, BikeRepository brepo) {
		return (args) -> {

			List<Vehicle> autoVehicles = Arrays.asList(
					new Vehicle("Audi", "Rs6", "Blue"),
					new Vehicle("Audi", "A5", "Gray"),
					new Vehicle("Ford", "Transit", "Black"),
					new Vehicle("VolksWagen", "Golf", "Black"),
					new Vehicle("VolksWagen", "Polo", "White"),
					new Vehicle("VolksWagen", "Polo", "Blue"),
					new Vehicle("Skoda", "Fabia", "Black"),
					new Vehicle("Skoda", "Octavia", "Red"),
					new Vehicle("Ford", "Mustang", "Black"),
					new Vehicle("Ford", "Focus", "Gray"),
					new Vehicle("Audi", "Q5", "Red"),
					new Vehicle("Audi", "A4", "Red"),
					new Vehicle("Tesla", "Model3", "White"),
					new Vehicle("Tesla", "Model Y", "Black"));
			autoVehicles.forEach(vehirepo::save);

			List<Car> autoLista = new ArrayList<>();
			autoLista.add(new Car("Car", "Diesel", 65, 21000, false, autoVehicles.get(0)));
			autoLista.add(new Car("Car", "Gasoline", 55, 110000, true, autoVehicles.get(1)));
			autoLista.add(
					new Car("Car", "Gasoline", 40, 89000, false, autoVehicles.get(2)));
			autoLista.add(new Car("Car", "Diesel", 30, 110000, true, autoVehicles.get(3)));
			autoLista.add(new Car("Car", "Gasoline", 37, 81300, true, autoVehicles.get(4)));
			autoLista.add(new Car("Car", "Gasoline", 50, 11983, false, autoVehicles.get(5)));
			autoLista.add(new Car("Car", "Gasoline", 60, 203111, true, autoVehicles.get(6)));
			autoLista.add(new Car("Car", "Gasoline", 55, 55321, false, autoVehicles.get(7)));
			autoLista.add(new Car("Car", "Gasoline", 70, 92061, false, autoVehicles.get(8)));
			autoLista.add(new Car("Car", "Diesel", 27, 136000, false, autoVehicles.get(9)));
			autoLista.add(new Car("Car", "Diesel", 35, 360000, true, autoVehicles.get(10)));
			autoLista.add(new Car("Car", "Diesel", 40, 26200, true, autoVehicles.get(11)));
			autoLista.add(new Car("Car", "Electric", 50, 49200, true, autoVehicles.get(12)));
			autoLista.add(new Car("Car", "Electric", 55, 72200, true, autoVehicles.get(13)));
			autoLista.forEach(crepo::save);

			List<Vehicle> bikeVehicles = Arrays.asList(
					new Vehicle("Honda", "CBR600RR", "Red"),
					new Vehicle("Harley-Davidson", "Sportster Iron 883", "Black"),
					new Vehicle("BMW", "R1250GS Adventure", "White"),
					new Vehicle("Kawasaki", "Ninja 650", "Green"),
					new Vehicle("Yamaha", "MT-07", "Blue"),
					new Vehicle("Ducati", "Panigale V4", "Red"),
					new Vehicle("Suzuki", "Boulevard M109R", "Silver"),
					new Vehicle("Triumph", "Bonneville T100", "Black"),
					new Vehicle("KTM", "1290 Super Duke R", "Orange"),
					new Vehicle("Indian", "Scout Bobber", "Brown"));
			bikeVehicles.forEach(vehirepo::save);

			List<Bike> bikeLista = new ArrayList<>();
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 25, 1000, false, bikeVehicles.get(0)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 25, 10000, true, bikeVehicles.get(1)));
			bikeLista.add(
					new Bike("Motorcycle", "Gasoline", 40, 9000, false, bikeVehicles.get(2)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 30, 10000, true, bikeVehicles.get(3)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 37, 1300, true, bikeVehicles.get(4)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 30, 1983, false, bikeVehicles.get(5)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 10, 20111, true, bikeVehicles.get(6)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 35, 5521, false, bikeVehicles.get(7)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 25, 92061, false, bikeVehicles.get(8)));
			bikeLista.add(new Bike("Motorcycle", "Gasoline", 27, 13600, false, bikeVehicles.get(9)));

			bikeLista.forEach(brepo::save);

			log.info("kaikkii vehicel -->");
			for (Bike veh : brepo.findAll()) {
				log.info(veh.toString());
			}

			/*
			 * log.info("kaikki autot -->");
			 * for (Car car : crepo.findAll()) {
			 * log.info(car.toString());
			 * }
			 * log.info("kaikki mopot -->");
			 * for (Bike b : brepo.findAll()) {
			 * log.info(b.toString());
			 * }
			 */

		};
	}
}
