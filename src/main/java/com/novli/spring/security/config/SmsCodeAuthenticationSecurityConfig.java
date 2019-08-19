package com.novli.spring.security.config;

import com.novli.spring.security.authentication.SecurityFailureHandler;
import com.novli.spring.security.authentication.SecuritySuccessHandler;
import com.novli.spring.security.authentication.mobile.SmsCodeAuthenticationFilter;
import com.novli.spring.security.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SecuritySuccessHandler securitySuccessHandler;

    @Autowired
    private SecurityFailureHandler securityFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http){

        SmsCodeAuthenticationFilter sms = new SmsCodeAuthenticationFilter();
        sms.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        sms.setAuthenticationSuccessHandler(securitySuccessHandler);
        sms.setAuthenticationFailureHandler(securityFailureHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
            .addFilterAfter(sms, UsernamePasswordAuthenticationFilter.class);

    }
}
