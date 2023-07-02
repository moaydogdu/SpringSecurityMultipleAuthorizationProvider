package com.moaydogdu.ssexample6.security.providers;

import com.moaydogdu.ssexample6.security.authentication.UsernamePasswordAuthentication;
import com.moaydogdu.ssexample6.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsernamePasswordAuthenticationProvider
implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();


        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuthentication(
                    username,password, user.getAuthorities());
        }

        throw new BadCredentialsException("Error finding user.");
    }

    @Override
    public boolean supports(
            Class<?> authenticationType
    ) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
