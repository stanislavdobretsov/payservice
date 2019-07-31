package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.PayOperation;
import com.dobretsov.payservice.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PayOperationRepositoryImpl implements PayOperationRepository {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public PayOperationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("pay_operation").
                usingGeneratedKeyColumns("pay_operation_id");
    }

    @Override
    public PayOperation save(PayOperation payOperation) {
        int id = simpleInsert.executeAndReturnKey(mapPayOperation(payOperation)).intValue();
        return findById(id);
    }

    @Override
    public PayOperation findById(Integer id) {
        List<PayOperation> result = null;
        result = jdbc.query("SELECT * FROM pay_operation WHERE pay_operation_id = ?", new Object[] {id}, this::payOperationMapRow);
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<PayOperation> findFiltered(Map<String, Object> filter) {
        Integer clientId = (Integer)filter.get("clientId");
        LocalDateTime begin = (LocalDateTime)filter.get("begin");
        LocalDateTime end = (LocalDateTime)filter.get("end");
        Integer serviceId = (Integer)filter.get("serviceId");
        BigDecimal paySum = (BigDecimal)filter.get("paySum");
        List<PayOperation> result = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM pay_operation WHERE (client_id = ?) AND (?::TIMESTAMP IS NULL AND ?::TIMESTAMP IS NULL ");
        sql.append("OR operation_time >= ? AND operation_time <= ?) ");
        sql.append("AND (?::INT IS NULL OR service_id = ?) ");
        sql.append("AND (?::INT IS NULL OR pay_sum = ?)");
        result = jdbc.query(sql.toString(), new Object[]{clientId, begin, end, begin, end, serviceId, serviceId,
                paySum, paySum}, this::payOperationMapRow);
        return result;
    }

    private PayOperation payOperationMapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PayOperation payOperation = new PayOperation();
        payOperation.setClientId(resultSet.getInt("client_id"));
        payOperation.setOperationTime(resultSet.getTimestamp("operation_time").toLocalDateTime());
        payOperation.setPayAccountNumber(resultSet.getString("pay_account_number"));
        payOperation.setPayOperationId(resultSet.getInt("pay_operation_id"));
        payOperation.setPaySum(resultSet.getBigDecimal("pay_sum"));
        payOperation.setServiceId(resultSet.getInt("service_id"));
        return payOperation;
    }

    private Map<String, Object> mapPayOperation(PayOperation payOperation) {
        Map<String, Object> map = new HashMap<>();
        map.put("client_id", payOperation.getClientId());
        map.put("operation_time", payOperation.getOperationTime());
        map.put("pay_account_number", payOperation.getPayAccountNumber());
        map.put("pay_operation_id", payOperation.getPayOperationId());
        map.put("pay_sum", payOperation.getPaySum());
        map.put("service_id", payOperation.getServiceId());
        return map;
    }

}
