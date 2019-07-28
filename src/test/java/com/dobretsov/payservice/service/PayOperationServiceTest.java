package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.PayOperationRepository;
import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.PayOperation;
import com.dobretsov.payservice.domain.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class PayOperationServiceTest {

    @Mock
    private PayOperationRepository repo;

    @InjectMocks
    private PayOperationService payOperationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerPayOperationTest() {
        PayOperation payOperation = new PayOperation();
        payOperation.setServiceId(1);
        payOperation.setClientId(1);
        payOperation.setOperationTime(LocalDateTime.now());
        payOperation.setPayAccountNumber("452345543");
        payOperation.setPaySum(new BigDecimal(100));

        Client client = new Client();
        client.setClientId(payOperation.getClientId());
        client.setPhoneNumber("+755500000");
        Service service = new Service();
        service.setServiceId(payOperation.getServiceId());
        service.setTitle("МТС");

        Mockito.when(repo.save(payOperation)).thenReturn(payOperation);

        PayOperation registeredPayOperation = payOperationService
                .registerPayOperation(client, service, payOperation.getPayAccountNumber(), payOperation.getPaySum(), payOperation.getOperationTime());

        assertEquals("Payment is not registered", payOperation, registeredPayOperation);

    }

}
