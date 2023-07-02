package com.moaydogdu.ssexample6.config;

import com.moaydogdu.ssexample6.security.filters.UsernamePasswordAuthFilter;
import com.moaydogdu.ssexample6.security.providers.OtpAuthenticationProvider;
import com.moaydogdu.ssexample6.security.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfiguration {

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;



    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity
    )
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
         authenticationManagerBuilder
                .authenticationProvider(usernamePasswordAuthenticationProvider)
                 .authenticationProvider(otpAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }


}
