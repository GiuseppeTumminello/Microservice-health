package com.acoustic.service;

import com.acoustic.rate.RatesConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class HealthServiceTest {

    @InjectMocks
    private HealthService healthService;

    @Mock
    private RatesConfigurationProperties ratesConfigurationProperties;

    @Test
    void getDescription() {
        assertThat(this.healthService.getDescription()).isEqualTo("Health zus");
    }

    @ParameterizedTest
    @CsvSource({"5177.4, 465.97, 0.09", "6040.30, 543.63, 0.09", "13712.93, 1234.16, 0.09"})
    public void getHealth(BigDecimal input, BigDecimal expected, BigDecimal rate) {
        given(this.ratesConfigurationProperties.getHealthRate()).willReturn(rate);
        assertThat(this.healthService.apply(input)).isEqualTo(expected);
    }
}