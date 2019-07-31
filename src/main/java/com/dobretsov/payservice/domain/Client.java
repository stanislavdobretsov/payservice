package com.dobretsov.payservice.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class Client {

    private Integer clientId;
    @NotEmpty(message = "Phone number can't be empty")
    @Pattern(regexp = "[0-9]{11}", message = "Please provide correct phone number")
    private String phoneNumber;
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Please provide correct email")
    private String email;
    private String password;
    private BigDecimal credits;

}
