package com.dobretsov.payservice.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Service {

    private Integer serviceId;
    private String title;
    private BigDecimal minPaySum;
    private BigDecimal maxPaySum;

}
