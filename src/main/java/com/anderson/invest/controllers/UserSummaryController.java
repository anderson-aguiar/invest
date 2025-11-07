package com.anderson.invest.controllers;

import com.anderson.invest.dtos.UserSummaryResponseDTO;
import com.anderson.invest.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserSummaryController {

    private final UserService userService;

    public UserSummaryController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}/summary")
    public ResponseEntity<UserSummaryResponseDTO> summary(@PathVariable Long id, Principal principal) {
        String email = principal.getName();

        UserSummaryResponseDTO response = userService.summary(email, id);

        return ResponseEntity.ok(response);
    }
}
