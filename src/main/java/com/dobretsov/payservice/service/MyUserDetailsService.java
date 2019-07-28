package com.dobretsov.payservice.service;

import com.dobretsov.payservice.dao.ClientRepository;
import com.dobretsov.payservice.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Client client = repo.findByPhoneNumber(username);
        if (client == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(client);
    }
}