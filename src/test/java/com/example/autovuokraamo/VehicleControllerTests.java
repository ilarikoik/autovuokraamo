
package com.example.autovuokraamo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest

@AutoConfigureMockMvc
public class VehicleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void vehicleList() throws Exception {
        this.mockMvc.perform(get("/vehicles")).andExpect(status().isOk());
    }

    @Test
    public void vehicleListbyColor() throws Exception {
        this.mockMvc.perform(get("/allvehiclesby/Honda")).andExpect(status().isOk());
    }

    @Test
    public void vehicleListContains() throws Exception {
        this.mockMvc.perform(get("/vehicles")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Vehicles by Brand")))
                .andExpect(content().string(
                        containsString("Merkki")));
    }

}
