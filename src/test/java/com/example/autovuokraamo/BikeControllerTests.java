package com.example.autovuokraamo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class BikeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBy() throws Exception {
        this.mockMvc.perform(get("/bikebycolor/Valkoinen")).andExpect(status().isOk());
    }

    @Test
    public void testDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/brented"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBikes() throws Exception {
        this.mockMvc.perform(get("/bikes")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("BIKES")));
    }

    @Test
    void shouldReturnHae() throws Exception {
        this.mockMvc.perform(get("/bikes")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Merkki")))
                .andExpect(content().string(containsString("Malli")))
                .andExpect(content().string(containsString("Väri")))
                .andExpect(content().string(containsString("Hinta")));
    }

    @Test
    void shouldReturnMerkinPerusteella() throws Exception {
        this.mockMvc.perform(get("/bikes")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hae mopoja merkin perusteella ")));
    }

}
