package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ClientRepository;
import com.dobretsov.payservice.dao.DaoException;
import com.dobretsov.payservice.dao.PayOperationRepository;
import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.PayOperation;
import com.dobretsov.payservice.domain.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientAccountTest {

    @Mock
    private ClientRepository repo;
    @Mock
    private PayOperationRepository payOperationRepository;

    @InjectMocks
    private PayOperationService service;

    @InjectMocks
    private ClientAccount account;

    @Before
    public void setUp() {
        service = Mockito.spy(new PayOperationService());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findClientTest() {
        Client client = new Client();
        client.setClientId(1);
        client.setCredits(new BigDecimal(100));
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
        client.setCredits(new BigDecimal(100));
        client.setPassword("3v34v3");
        client.setEmail("any@mail.com");
        client.setPhoneNumber("+755500000");
        Mockito.when(repo.save(client)).thenReturn(client);
        assertEquals("Client is not registered", client, account.registerClient(client));
    }

    @Test
    public void performPayOperationTest() throws ServiceException, DaoException {
        Service service = new Service();
        service.setTitle("MTC");
        service.setServiceId(1);
        service.setMinPaySum(new BigDecimal(20));
        service.setMaxPaySum(new BigDecimal(90));
        Client client = new Client();
        client.setPassword("evewrve");
        client.setCredits(new BigDecimal(100));
        client.setPhoneNumber("+755500000");
        client.setClientId(1);
        client.setEmail("any@mail.com");
        PayOperation payOperation = new PayOperation();
        LocalDateTime dateTime = LocalDateTime.now();
        payOperation.setOperationTime(dateTime);
        payOperation.setPayOperationId(1);
        payOperation.setPayAccountNumber("23432432");
        payOperation.setClientId(1);
        payOperation.setServiceId(1);
        payOperation.setPaySum(new BigDecimal(50));
        Mockito.when(payOperationRepository.save(payOperation)).thenReturn(payOperation);
        Mockito.when(repo.updateCredits(1, new BigDecimal(30))).thenReturn(1);
        account.performPayOperation(client, service, payOperation.getPayAccountNumber(), payOperation.getPaySum(), dateTime);
    }

}
