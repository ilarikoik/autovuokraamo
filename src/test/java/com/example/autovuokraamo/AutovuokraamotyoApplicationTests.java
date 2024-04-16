package com.example.autovuokraamo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.autovuokraamo.domain.Bike;
import com.example.autovuokraamo.web.BikeController;
import com.example.autovuokraamo.web.CarController;
import com.example.autovuokraamo.web.VehicleController;

@SpringBootTest
class AutovuokraamotyoApplicationTests {

	@Autowired
	private BikeController bc;
	@Autowired
	private CarController cc;
	@Autowired
	private VehicleController vc;

	@Test
	void contextLoadsbc() throws Exception {
		assertThat(bc).isNotNull();
	}

	@Test
	void contextLoadscc() throws Exception {
		assertThat(cc).isNotNull();
	}

	@Test
	void contextLoadsvc() throws Exception {
		assertThat(vc).isNotNull();
	}

}
