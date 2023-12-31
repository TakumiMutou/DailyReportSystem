package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.repository.AuthenticationRepository;

@Service
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository repository) {
        this.authenticationRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Authentication> getAuthenticationList() {

        return authenticationRepository.findAll();
    }

    @Transactional
    public Authentication saveAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

}
