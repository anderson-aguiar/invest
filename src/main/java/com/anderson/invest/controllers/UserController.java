package com.anderson.invest.controllers;

import com.anderson.invest.dtos.LoginRequestDTO;
import com.anderson.invest.dtos.LoginResponseDTO;
import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.services.AuthService;
import com.anderson.invest.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserMinDTO> insert(@RequestBody @Valid UserRequestDTO requestDTO){
        UserMinDTO response = userService.insert(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO){
        String token = authService.authenticateAndGenerateToken(requestDTO);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
