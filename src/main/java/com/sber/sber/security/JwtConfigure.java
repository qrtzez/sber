package com.sber.sber.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

//@Component
public class JwtConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //builder.addFilter(jwtTokenFilter)
        super.configure(builder);
    }
}
