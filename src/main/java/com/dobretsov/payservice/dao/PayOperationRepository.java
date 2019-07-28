package com.dobretsov.payservice.dao;

import com.dobretsov.payservice.domain.PayOperation;

import java.util.List;
import java.util.Map;

public interface PayOperationRepository {

    PayOperation save(PayOperation payOperation);
    PayOperation findById(Integer id);
    List<PayOperation> findFiltered(Map<String, Object> filter);

}
