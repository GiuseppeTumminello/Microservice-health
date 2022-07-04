package com.acoustic.integrationtestcontroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class HealthTest {

    @Autowired
    private MockMvc mockMvc;
   @Autowired
   private ObjectMapper objectMapper;

    private final String TOTAL_ZUS_ENDPOINT = "/health/getHealth/";


    @ParameterizedTest
    @CsvSource({"6000,465.97", "7000, 543.63", "8555,664.39", "15143.99,1176.10"})
    public void calculateHealth(BigDecimal input, BigDecimal health) throws Exception {
        var expected = this.objectMapper.writeValueAsString(Map.of("Health zus", health));
        this.mockMvc.perform(post(TOTAL_ZUS_ENDPOINT + input).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

}