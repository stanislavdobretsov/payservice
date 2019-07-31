package com.dobretsov.payservice.web;

import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.domain.PayOperation;
import com.dobretsov.payservice.domain.Service;
import com.dobretsov.payservice.service.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class Payments {

    private static class FilterWrapper {
        @Getter
        private Map<String, Object> filter;

        public FilterWrapper() {
            filter = new HashMap<>();
        }

        public void setBegin(String begin) {
            filter.put("begin", LocalDateTime.parse(begin, DateTimeFormatter.ISO_DATE_TIME));
        }

        public String getBegin() {
            return null;
        }

        public void setEnd(String end) {
            filter.put("end", LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME));
        }

        public String getEnd() {
            return null;
        }

        public void setServiceId(Integer serviceId) {
            filter.put("serviceId", serviceId);
        }

        public Integer getServiceId() {
            return (Integer) filter.get("serviceId");
        }

        public void setPaySum(BigDecimal paySum) {
            filter.put("paySum", paySum);
        }

        public BigDecimal getPaySum() {
            return (BigDecimal) filter.get("paySum");
        }

    }

    @Autowired
    private PayOperationService service;
    @Autowired
    private ServiceRegistry registry;
    @Autowired
    private ClientAccount account;

    @GetMapping("/payment")
    public String paymentForm(Model model) {
        model.addAttribute("payOperation", new PayOperation());
        model.addAttribute("services", registry.getAllServices());
        return "payment";
    }

    @PostMapping("/payment")
    public String processPayment(Model model, @Valid @ModelAttribute("payOperation") PayOperation payOperation, BindingResult bindingResult) throws ServiceException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("services", registry.getAllServices());
            return "payment";
        }
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = account.findClient(principal.getUsername());
        account.performPayOperation(client, registry.getServiceById(payOperation.getServiceId()), payOperation.getPayAccountNumber(),
                payOperation.getPaySum(), LocalDateTime.now());
        return "payment-success";
    }

    @ExceptionHandler({ServiceException.class})
    public String handlePaymentErrors(ServiceException exception) {
        log.error("Service exception: {}", exception.getMessage());
        return "payment-error";
    }

    @GetMapping("/reportpayments")
    public String reportForm(Model model, @ModelAttribute("filter") FilterWrapper filterWrapper) {
        if(filterWrapper == null) {
            model.addAttribute("filter", new FilterWrapper());
        }

        model.addAttribute("services", registry.getAllServices());
        return "payments-report";
    }

    @PostMapping("/reportpayments")
    public String buildReport(Model model, @ModelAttribute("filter") FilterWrapper filterWrapper, BindingResult bindingResult) {

        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = account.findClient(principal.getUsername());
        filterWrapper.getFilter().put("clientId", client.getClientId());
        List<PayOperation> operations = service.reportFilteredOperations(filterWrapper.getFilter());
        model.addAttribute("results", operations);
        model.addAttribute("services", registry.getAllServices());
        return "payments-report";
    }

}
