package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.PayOperationRepository;
import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.PayOperation;
import com.dobretsov.payservice.domain.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
@Slf4j
public class PayOperationService {

    @Autowired
    private PayOperationRepository repo;

    @Transactional("dsTransactionManager")
    public PayOperation registerPayOperation(Client client, Service service, String payAccountNumber, BigDecimal paySum, LocalDateTime operationTime) {
        PayOperation payOperation = new PayOperation();
        payOperation.setPaySum(paySum);
        payOperation.setPayAccountNumber(payAccountNumber);
        payOperation.setOperationTime(operationTime);
        payOperation.setClientId(client.getClientId());
        payOperation.setServiceId(service.getServiceId());
        PayOperation registeredPayOperation = repo.save(payOperation);
        log.info("Payment " + service.getTitle() + " " + paySum.toString() + " credits for client " +
                client.getPhoneNumber() + " is performed to " + payAccountNumber);
        return registeredPayOperation;
    }

    public List<PayOperation> reportFilteredOperations(Map<String, Object> filter) {
        return repo.findFiltered(filter);
    }

}
