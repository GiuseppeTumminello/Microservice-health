package com.acoustic.controller;


import com.acoustic.entity.Health;
import com.acoustic.repository.HealthRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
@CrossOrigin
public class HealthController {

    public static final String DESCRIPTION = "description";
    public static final String VALUE = "value";
    private final HealthRepository healthRepository;
    private final SalaryCalculatorService salaryCalculatorService;


    @PostMapping("/calculation/{grossMonthlySalary}")
    public Map<String, String> calculateHealth(@PathVariable @Min(2000) BigDecimal grossMonthlySalary) {
        var health = this.salaryCalculatorService.apply(grossMonthlySalary);
        this.healthRepository.save(Health.builder().healthAmount(health).build());
        return Map.of(DESCRIPTION, this.salaryCalculatorService.getDescription(), VALUE, String.valueOf(health));
    }
}
