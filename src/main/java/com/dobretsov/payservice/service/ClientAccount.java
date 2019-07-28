package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ClientRepository;
import com.dobretsov.payservice.domain.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class ClientAccount {

    @Autowired
    private ClientRepository repo;

    @Transactional("dsTransactionManager")
    public Client registerClient(Client client) {
        Client registeredClient = repo.save(client);
        log.info("Client with phone number " + registeredClient.getPhoneNumber() + " is registered!");
        return registeredClient;
    }

    public Client findClient(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber);
    }

    @Transactional("dsTransactionManager")
    public void performPayOperation(Client client, BigDecimal paySum) {
        repo.updateCredits(client.getClientId(), client.getCredits().subtract(paySum));
        log.info("Performing payment for client " + client.getPhoneNumber());
    }

}
