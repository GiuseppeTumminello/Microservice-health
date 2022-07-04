package com.acoustic.controller;


import com.acoustic.entity.Health;
import com.acoustic.rate.RatesConfigurationProperties;
import com.acoustic.repository.HealthRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

    public static final String TOTAL_ZUS_DESCRIPTION = "Total zus";
    private final HealthRepository healthRepository;
    private final SalaryCalculatorService salaryCalculatorService;
    private final RatesConfigurationProperties ratesConfigurationProperties;
    private static final String TOTAL_ZUS_ENDPOINT = "http://TOTAL-ZUS/totalZus/getTotalZus/";

    private final RestTemplate restTemplate;


    @PostMapping("/getHealth/{grossMonthlySalary}")
    public Map<String, BigDecimal> calculateHealth(@PathVariable @Min(2000)BigDecimal grossMonthlySalary){
        var totalZus = Objects.requireNonNull(this.restTemplate.postForEntity(TOTAL_ZUS_ENDPOINT + grossMonthlySalary, HttpMethod.POST, HashMap.class).getBody()).get(TOTAL_ZUS_DESCRIPTION);
        var health = this.salaryCalculatorService.apply(grossMonthlySalary.subtract((BigDecimal.valueOf(((Double)(totalZus))))));
        this.healthRepository.save(Health.builder().healthAmount(health).healthRate(this.ratesConfigurationProperties.getHealthRate()).build());
        return new LinkedHashMap<>(Map.of(this.salaryCalculatorService.getDescription(), health));
    }
}
