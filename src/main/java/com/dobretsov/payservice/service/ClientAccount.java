package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ClientRepository;
import com.dobretsov.payservice.dao.DaoException;
import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@org.springframework.stereotype.Service
@Slf4j
public class ClientAccount {

    public static final int DEFAULT_PASSWORD_LENGTH = 8;
    @Autowired
    private ClientRepository repo;
    @Autowired
    private PayOperationService payOperationService;

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
    public void performPayOperation(Client client, Service service, String accountNumber, BigDecimal paySum, LocalDateTime operationTime) throws ServiceException {
        try {
            repo.updateCredits(client.getClientId(), client.getCredits().subtract(paySum));
        } catch (DaoException e) {
            log.error("Dao exception: {}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        log.info("Performing payment for client " + client.getPhoneNumber());
        payOperationService.registerPayOperation(client, service, accountNumber, paySum, operationTime);
    }

    public String generatePassword(int length) {
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";
        final Random randomizer = new Random();
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int number = randomizer.nextInt();
            int index;
            if(randomizer.nextBoolean()) {
                index = (number < 0 ? number * - 1 : number) % 26;
                password.append(letters.charAt(index));
            } else {
                index = (number < 0 ? number * - 1 : number) % 10;
                password.append(digits.charAt(index));
            }
        }
        return password.toString();
    }

}
