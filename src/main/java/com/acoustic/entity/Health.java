package com.acoustic.entity;


import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private BigDecimal healthAmount;

    private BigDecimal healthRate;
}
