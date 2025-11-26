package com.example.eureka.auth.aplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    public boolean hasAccess(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        log.info("User: {} has access", username);
        auth.getAuthorities().forEach(e -> log.info("Role: {}", e.getAuthority()));

        return true;
    }
}
