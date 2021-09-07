package com.ming.mall.security.config;

import com.ming.mall.security.component.IUserDetailsService;
import com.ming.mall.security.email.EmailAuthenticationFilter;
import com.ming.mall.security.email.EmailAuthenticationProvider;
import com.ming.mall.security.email.EmailFailureHandler;
import com.ming.mall.security.email.EmailSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: ming
 * @create: 2021-09-07 14:21
 * @program: demo-mall
 */
@Component
public class EmailSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private IUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSuccessHandler successHandler;

    @Autowired
    private EmailFailureHandler failureHandler;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        EmailAuthenticationFilter emailAuthenticationFilter = new EmailAuthenticationFilter("/admin/email/login");
        emailAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        emailAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        emailAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        EmailAuthenticationProvider provider = new EmailAuthenticationProvider(userDetailsService, passwordEncoder);
        builder.authenticationProvider(provider).addFilterBefore(emailAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
