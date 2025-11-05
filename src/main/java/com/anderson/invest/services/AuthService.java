package com.anderson.invest.services;

import com.anderson.invest.dtos.LoginRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.exceptions.InvalidTokenException;
import com.anderson.invest.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public List<String> authenticateAndGenerateToken(LoginRequestDTO requestDTO) {
        List<String> tokens = new ArrayList<>();
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        tokens.add(tokenService.generateAccessToken(user));
        tokens.add(tokenService.generateRefreshToken(user));
        return tokens;
    }
    public String refreshAccessToken(String refreshToken){
        String email = tokenService.validateRefreshToken(refreshToken);

        if(email.isEmpty()){
            throw new InvalidTokenException("Refresh token inválido ou expirado. Novo login necessário");
        }
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        return tokenService.generateAccessToken(user);
    }
}
