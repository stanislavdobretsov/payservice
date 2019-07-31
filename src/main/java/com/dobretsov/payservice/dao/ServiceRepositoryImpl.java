package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository{

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public ServiceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("service").
                usingGeneratedKeyColumns("service_id");
    }

    @Override
    public Service save(Service service) {
         int id = simpleInsert.executeAndReturnKey(mapService(service)).intValue();
         return findById(id);
    }

    @Override
    public Service findById(Integer id) {
        List<Service> result = null;
        result = jdbc.query("SELECT * FROM service WHERE service_id = ?", new Object[] {id}, this::serviceMapRow);
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public Service findByTitle(String title) {
        List<Service> result = null;
        result = jdbc.query("SELECT * FROM service WHERE title = ?", new Object[] {title}, this::serviceMapRow);
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<Service> findAll() {
        return jdbc.query("SELECT service_id, title, min_pay_sum, max_pay_sum FROM service", this::serviceMapRow);
    }

    private Service serviceMapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Service service = new Service();
        service.setMinPaySum(resultSet.getBigDecimal("min_pay_sum"));
        service.setMaxPaySum(resultSet.getBigDecimal("max_pay_sum"));
        service.setServiceId(resultSet.getInt("service_id"));
        service.setTitle(resultSet.getString("title"));
        return service;
    }

    private Map<String, Object> mapService(Service service) {
        Map<String, Object> map = new HashMap<>();
        map.put("min_pay_sum", service.getMinPaySum());
        map.put("max_pay_sum", service.getMaxPaySum());
        map.put("service_id", service.getServiceId());
        map.put("title", service.getTitle());
        return map;
    }

}
