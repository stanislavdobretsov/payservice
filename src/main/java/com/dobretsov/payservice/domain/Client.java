package com.dobretsov.payservice.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class Client {

    private Integer clientId;
    @Pattern(regexp = "([+]?)[0-9]{11}", message = "Please provide correct phone number")
    private String phoneNumber;
    @Email(message = "Please provide correct email")
    private String email;
    private String password;
    private BigDecimal credits;

}
