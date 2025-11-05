package com.anderson.invest.controllers;

import com.anderson.invest.dtos.*;
import com.anderson.invest.services.AuthService;
import com.anderson.invest.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserMinDTO> insert(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserMinDTO response = userService.insert(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        List<String> tokens = authService.authenticateAndGenerateToken(requestDTO);

        return ResponseEntity.ok(new LoginResponseDTO(tokens.get(0), tokens.get(1)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody @Valid RefreshTokenRequestDTO requestDTO) {
        try {
            String newAccessToken = authService.refreshAccessToken(requestDTO.getRefreshToken());
            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, requestDTO.getRefreshToken()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
