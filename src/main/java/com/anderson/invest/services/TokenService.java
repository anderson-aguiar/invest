package com.anderson.invest.services;

import com.anderson.invest.entities.User;
import com.anderson.invest.exceptions.InvalidTokenException;
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
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.duration}")
    private Long duration;

    @Value("${security.jwt.refresh-duration}")
    private Long refreshDuration;

    public String generateAccessToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("access-token") // Quem emitiu o token
                    .withSubject(user.getEmail()) // O usuário principal (email)
                    .withExpiresAt(getExpirationDate(duration)) // Data de expiração
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new InvalidTokenException("Erro ao gerar token JWT");
        }
    }
    public String generateRefreshToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("refresh-token") // Quem emitiu o token
                    .withSubject(user.getEmail()) // O usuário principal (email)
                    .withExpiresAt(getExpirationDate(refreshDuration)) // Data de expiração
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new InvalidTokenException("Erro ao gerar refresh-token JWT");
        }
    }

    public String validateToken(String token) {
        try {
            // Define o algoritmo para verificação.
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("access-token")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o subject (email).

        } catch (JWTVerificationException exception) {
            return "";
        }
    }
    public String validateRefreshToken(String token) {
        try {
            // Define o algoritmo para verificação.
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("refresh-token")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o subject (email).

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant getExpirationDate(Long duration) {
        return LocalDateTime.now().plusSeconds(duration).toInstant(ZoneOffset.of("-03:00"));
    }
}
