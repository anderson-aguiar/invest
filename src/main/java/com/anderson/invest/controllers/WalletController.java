package com.anderson.invest.controllers;

import com.anderson.invest.dtos.*;
import com.anderson.invest.services.InvestmentService;
import com.anderson.invest.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private InvestmentService investmentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WalletInsertResponseDTO> insert(@RequestBody @Valid WalletRequestDTO requestDTO, Principal principal) {
        String email = principal.getName();
        WalletInsertResponseDTO responseDTO = walletService.insert(requestDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WalletMinDTO>> findAllWallets(Principal principal) {
        String email = principal.getName();

        List<WalletMinDTO> wallets = walletService.findAllWallets(email);

        return ResponseEntity.ok(wallets);
    }

    @PostMapping("/{id}/investments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InvestmentResponseDTO> addInvestment(
            @RequestBody @Valid InvestmentRequestDTO requestDTO, @PathVariable Long id, Principal principal) {

        String email = principal.getName();
        InvestmentResponseDTO responseDTO = investmentService.insert(requestDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @GetMapping("/{walletId}/investments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InvestmentResponseDTO>> findAllInvestments(@PathVariable Long walletId, Principal principal) {
        String email = principal.getName();
        List<InvestmentResponseDTO> investments = investmentService.findAllInvest(email, walletId);

        return ResponseEntity.ok(investments);
    }
}
