package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.Client;

import java.math.BigDecimal;
import java.util.List;

public interface ClientRepository {

    Client save(Client client);
    Client findById(Integer id);
    Client findByPhoneNumber(String phoneNumber);
    Integer updateCredits(Integer id, BigDecimal credits);

}
