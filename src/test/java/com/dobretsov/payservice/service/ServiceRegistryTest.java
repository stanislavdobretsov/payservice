package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ServiceRepository;
import com.dobretsov.payservice.domain.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import java.math.BigDecimal;

public class ServiceRegistryTest {

    @Mock
    private ServiceRepository repo;

    @InjectMocks
    private ServiceRegistry registry;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getServiceTest() {
        Service service = new Service();
        service.setTitle("МТС");
        service.setServiceId(1);
        service.setMaxPaySum(new BigDecimal(10));
        service.setMaxPaySum(new BigDecimal(50));
        Mockito.when(repo.findByTitle("МТС")).thenReturn(service);
        assertEquals("Service is not found", service, registry.getService("МТС"));
    }
}
