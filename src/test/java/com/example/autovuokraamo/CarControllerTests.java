package com.example.autovuokraamo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void carList() throws Exception {
        this.mockMvc.perform(get("/cars")).andExpect(status().isOk());
    }

    @Test
    public void carListRented() throws Exception {
        this.mockMvc.perform(get("/rented")).andExpect(status().isOk());
    }

    @Test
    public void carListNotRented() throws Exception {
        this.mockMvc.perform(get("/notrented")).andExpect(status().isOk());
    }

    @Test
    public void carListbyColor() throws Exception {
        this.mockMvc.perform(get("/bycolor/Black")).andExpect(status().isOk());
    }

    @Test
    public void carListContains() throws Exception {
        this.mockMvc.perform(get("/cars")).andExpect(status().isOk())
                .andExpect(content().string(containsString("CARS")))
                .andExpect(content().string(containsString("Hae vapaana olevat autot merkin mukaan")))
                .andExpect(content().string(containsString("Hae vuokrattu/vuokraamaton")))
                .andExpect(content().string(containsString("Malli")))
                .andExpect(content().string(containsString("Merkki")));
    }

}
