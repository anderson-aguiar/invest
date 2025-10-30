package com.anderson.invest.controllers;

import com.anderson.invest.dtos.WalletInsertResponseDTO;
import com.anderson.invest.dtos.WalletRequestDTO;
import com.anderson.invest.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WalletInsertResponseDTO> insert(@RequestBody @Valid WalletRequestDTO requestDTO, Principal principal) {
        String email = principal.getName();
        WalletInsertResponseDTO responseDTO = walletService.insert(requestDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
