package com.moaydogdu.ssexample6.service;

import com.moaydogdu.ssexample6.repository.UserRepository;
import com.moaydogdu.ssexample6.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {

        var user = userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found."));

        return new SecurityUser(user);
    }
}
