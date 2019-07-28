package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public ClientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("client").
                usingGeneratedKeyColumns("client_id");
    }

    @Override
    public Client save(Client client) {
        int id = simpleInsert.executeAndReturnKey(mapClient(client)).intValue();
        return findById(id);
    }

    @Override
    public Client findById(Integer id) {
        List<Client> result = null;
        result = jdbc.query("SELECT * FROM client WHERE client_id = ?", new Object[] {id}, this::clientMapRow);
        return !result.isEmpty() ? result.get(0): null;
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        List<Client> result = null;
        result = jdbc.query("SELECT * FROM client WHERE phone_number = ?", new Object[] {phoneNumber}, this::clientMapRow);
        return !result.isEmpty() ? result.get(0): null;
    }

    @Override
    public Integer updateCredits(Integer id, BigDecimal credits) {
        return jdbc.update("UPDATE client SET credits=? WHERE client_id=?", credits, id);
    }

    private Client clientMapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Client client = new Client();
        client.setPhoneNumber(resultSet.getString("phone_number"));
        client.setClientId(resultSet.getInt("client_id"));
        client.setCredits(resultSet.getBigDecimal("credits"));
        client.setEmail(resultSet.getString("email"));
        client.setPassword(resultSet.getString("password"));
        return client;
    }

    private Map<String, Object> mapClient(Client client) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone_number", client.getPhoneNumber());
        map.put("client_id", client.getClientId());
        map.put("credits", client.getCredits());
        map.put("email", client.getEmail());
        map.put("password", client.getPassword());
        return map;
    }

}
