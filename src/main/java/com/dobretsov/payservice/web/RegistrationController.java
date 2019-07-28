package com.dobretsov.payservice.web;

import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.service.ClientAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private ClientAccount account;

    @GetMapping("/registration")
    public String showForm(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(Model model, @Valid @ModelAttribute("client") Client client, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "registration";
        }

        client.setEmail("fqwefewq");
        client.setPassword("vrevr");
        client.setCredits(new BigDecimal(9000));
        client = account.registerClient(client);
        model.addAttribute("number", client.getPhoneNumber());
        return "success";
    }

    @GetMapping("/getclient")
    @ResponseBody
    public Client getClient(@RequestParam("phonenumber") String phoneNumber) {
        return account.findClient(phoneNumber);
    }

}
