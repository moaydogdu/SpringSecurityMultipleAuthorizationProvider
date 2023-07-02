package com.moaydogdu.ssexample6.security.filters;

import com.moaydogdu.ssexample6.model.Otp;
import com.moaydogdu.ssexample6.repository.OtpRepository;
import com.moaydogdu.ssexample6.security.authentication.OtpAuthentication;
import com.moaydogdu.ssexample6.security.authentication.UsernamePasswordAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter
        extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if(otp == null){
            Authentication authentication = new UsernamePasswordAuthentication(username,password);
            authentication = authenticationManager.authenticate(authentication);

            String code = String.valueOf(new Random().nextInt(9999)+1000);

            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);

            //SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            Authentication authentication = new OtpAuthentication(username,otp);
            authentication = authenticationManager.authenticate(authentication);
            // we issue a token
            response.setHeader("Authorization", UUID.randomUUID().toString());

            //SecurityContextHolder.getContext().setAuthentication(authentication);
        }


    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) throws ServletException {
        return !request.getServletPath()
                .equals("/login");
    }
}
