package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ClientRepository;
import com.dobretsov.payservice.domain.Client;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class ClientAccountTest {

    @Mock
    private ClientRepository repo;

    @InjectMocks
    private ClientAccount account;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findClientTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setCredits(new BigDecimal(50));
        client.setPassword("3v34v3");
        client.setEmail("any@mail.com");
        client.setPhoneNumber("+755500000");
        Mockito.when(repo.findByPhoneNumber("+755500000")).thenReturn(client);
        assertEquals("Client is not found", client, account.findClient("+755500000"));
    }

    @Test
    public void registerClientTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setCredits(new BigDecimal(50));
        client.setPassword("3v34v3");
        client.setEmail("any@mail.com");
        client.setPhoneNumber("+755500000");
        Mockito.when(repo.save(client)).thenReturn(client);
        assertEquals("Client is not registered", client, account.registerClient(client));
    }

}
