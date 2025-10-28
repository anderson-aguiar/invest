package com.anderson.invest.services;

import com.anderson.invest.dtos.LoginRequestDTO;
import com.anderson.invest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String authenticateAndGenerateToken(LoginRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        return tokenService.generateToken(user);
    }
}
