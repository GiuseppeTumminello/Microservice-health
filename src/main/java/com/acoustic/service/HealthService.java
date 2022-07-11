package com.acoustic.service;

import com.acoustic.rate.RatesConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class HealthService implements SalaryCalculatorService{

    private final RatesConfigurationProperties ratesConfigurationProperties;

    @Override
    public String getDescription() {
        return "Health";
    }

    private BigDecimal calculateTotalZus(BigDecimal grossMonthlySalary){
        return grossMonthlySalary.subtract(grossMonthlySalary.multiply(this.ratesConfigurationProperties.getTotalZusRate()));
    }

    @Override
    public BigDecimal apply(final BigDecimal grossMonthlySalary) {
        return this.calculateTotalZus(grossMonthlySalary).multiply(this.ratesConfigurationProperties.getHealthRate()).setScale(2, RoundingMode.HALF_EVEN);
    }
}
