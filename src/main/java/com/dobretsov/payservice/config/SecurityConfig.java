package com.dobretsov.payservice.config;

import com.dobretsov.payservice.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationProvider provider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/payment").access("hasAuthority('User')")
                .antMatchers("/account").access("hasAuthority('User')")
                .antMatchers("/reportpayments").access("hasAuthority('User')")
                .antMatchers("/").permitAll()
                .antMatchers("/registration").permitAll()
                .and()
                .formLogin();
    }

    @Bean
    @Autowired
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}
