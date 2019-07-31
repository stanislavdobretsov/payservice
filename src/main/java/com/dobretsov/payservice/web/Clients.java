package com.dobretsov.payservice.web;

import com.dobretsov.payservice.domain.Client;
import com.dobretsov.payservice.service.ClientAccount;
import com.dobretsov.payservice.service.MyUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@Slf4j
public class Clients {

    @Autowired
    private ClientAccount account;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        client.setCredits(new BigDecimal(100));
        String password = account.generatePassword(ClientAccount.DEFAULT_PASSWORD_LENGTH);
        client.setPassword(passwordEncoder.encode(password));
        account.registerClient(client);
        model.addAttribute("password", password);
        return "registration-success";
    }

    @GetMapping("/account")
    public String account(Model model) {
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = account.findClient(principal.getUsername());
        model.addAttribute("client", client);
        return "account";
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping("/getclient")
    @ResponseBody
    public Client getClient(@RequestParam("phonenumber") String phoneNumber) {
        return account.findClient(phoneNumber);
    }

}
