package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.Service;

import java.util.List;

public interface ServiceRepository {

    Service save(Service service);
    Service findById(Integer id);
    Service findByTitle(String name);
    List<Service> findAll();

}
