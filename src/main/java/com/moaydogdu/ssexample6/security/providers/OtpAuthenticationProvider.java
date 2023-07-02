package com.moaydogdu.ssexample6.security.providers;

import com.moaydogdu.ssexample6.repository.OtpRepository;
import com.moaydogdu.ssexample6.security.authentication.OtpAuthentication;
import com.moaydogdu.ssexample6.security.authentication.UsernamePasswordAuthentication;
import com.moaydogdu.ssexample6.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthenticationProvider
        implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();


        var otpFromDatabase = otpRepository.findOtpByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Otp Not Found."));

        return new OtpAuthentication(username,otp, List.of(()->"read"));
    }

    @Override
    public boolean supports(
            Class<?> authenticationType
    ) {
        return authenticationType.equals(OtpAuthentication.class);
    }
}
