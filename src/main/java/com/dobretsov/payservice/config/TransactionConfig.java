package com.dobretsov.payservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionConfig {

    @Bean("dsTransactionManager")
    @Autowired
    public DataSourceTransactionManager dsTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
