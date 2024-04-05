package com.example.autovuokraamo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.autovuokraamo.domain.Car;
import com.example.autovuokraamo.domain.CarRepository;
import com.example.autovuokraamo.domain.Vehicle;
import com.example.autovuokraamo.domain.VehicleRepository;

@SpringBootApplication
public class AutovuokraamotyoApplication {

	private static final Logger log = LoggerFactory.getLogger(AutovuokraamotyoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AutovuokraamotyoApplication.class, args);
	}

	// bean commanline runner tallenna repoo pari testiä
	@Bean
	public CommandLineRunner carRental(CarRepository crepo, VehicleRepository vehirepo) {
		return (args) -> {

			List<Vehicle> vehicles = Arrays.asList(
					new Vehicle("audi", "Rs6", "Blue"),
					new Vehicle("audi", "A5", "Gray"),
					new Vehicle("Ford", "Transit", "Black"),
					new Vehicle("VolksWagen", "Golf", "Black"),
					new Vehicle("VolksWagen", "Polo", "White"),
					new Vehicle("VolksWagen", "Polo", "Blue"),
					new Vehicle("Skoda", "Fabia", "Black"),
					new Vehicle("Skoda", "Octavia", "Red"));
			vehicles.forEach(vehirepo::save);

			List<Car> lista = new ArrayList<>();
			lista.add(new Car("bensa", "henkilöauto", 59999, 21000, false, vehicles.get(0)));
			lista.add(new Car("diesel", "Pakettiauto", 13500, 110000, true, vehicles.get(1)));
			lista.add(
					new Car("bensa", "henkilöauto", 24999, 89000, false, vehicles.get(2)));
			lista.add(new Car("diesel", "Viistoperä", 11500, 110000, true, vehicles.get(3)));
			lista.add(new Car("bensa", "Viistoperä", 17500, 81300, true, vehicles.get(4)));
			lista.add(new Car("bensa", "Viistoperä", 17500, 11983, false, vehicles.get(5)));
			lista.add(new Car("bensa", "Farmari", 27500, 203111, true, vehicles.get(6)));
			lista.add(new Car("bensa", "Farmari", 15500, 55321, false, vehicles.get(7)));

			lista.forEach(crepo::save);

			log.info("kaikkii vehicel -->");
			for (Vehicle veh : vehirepo.findAll()) {
				log.info(veh.toString());
			}
			log.info("kaikki autot -->");
			for (Car car : crepo.findAll()) {
				log.info(car.toString());
			}

		};
	}
}
