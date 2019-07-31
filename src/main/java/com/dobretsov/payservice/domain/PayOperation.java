package com.dobretsov.payservice.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayOperation {

    private Integer payOperationId;
    private Integer clientId;
    private Integer serviceId;
    private LocalDateTime operationTime;
    @Size(max = 40, message = "Max length is 40 symbols")
    @NotEmpty(message = "Provide pay account number")
    private String payAccountNumber;
    @PositiveOrZero
    @NotNull(message = "Provide pay sum")
    private BigDecimal paySum;

}
