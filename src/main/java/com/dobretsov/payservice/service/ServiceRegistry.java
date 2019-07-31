package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ServiceRepository;
import com.dobretsov.payservice.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceRegistry {

    @Autowired
    private ServiceRepository repo;

    public Service getService(String title) {
        return repo.findByTitle(title);
    }

    public List<Service> getAllServices() {
        return repo.findAll();
    }

    public Service getServiceById(Integer serviceId) {
        return repo.findById(serviceId);
    }

}
