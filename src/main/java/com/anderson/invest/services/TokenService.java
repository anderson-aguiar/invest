package com.anderson.invest.services;

import com.anderson.invest.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${security.jwt.secret:MySecretKey}")
    private String secret;

    @Value("${security.jwt.duration:86400}")
    private Long duration;

    public String generateToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("invest-api") // Quem emitiu o token
                    .withSubject(user.getEmail()) // O usuário principal (email)
                    .withExpiresAt(getExpirationDate()) // Data de expiração
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            // Define o algoritmo para verificação.
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("invest-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o subject (email).

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusSeconds(duration).toInstant(ZoneOffset.of("-03:00"));
    }
}
