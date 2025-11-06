package com.anderson.invest.controllers;

import com.anderson.invest.dtos.*;
import com.anderson.invest.services.InvestmentService;
import com.anderson.invest.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("wallets")
public class WalletController {

    private final WalletService walletService;
    private final InvestmentService investmentService;

    public WalletController(WalletService walletService, InvestmentService investmentService) {
        this.walletService = walletService;
        this.investmentService = investmentService;
    }

    @PostMapping
    public ResponseEntity<WalletInsertResponseDTO> insert(@RequestBody @Valid WalletRequestDTO requestDTO, Principal principal) {
        String email = principal.getName();
        WalletInsertResponseDTO responseDTO = walletService.insert(requestDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<WalletMinDTO>> findAllWallets(Principal principal) {
        String email = principal.getName();

        List<WalletMinDTO> wallets = walletService.findAllWallets(email);

        return ResponseEntity.ok(wallets);
    }

    @PostMapping("/{id}/investments")
    public ResponseEntity<InvestmentResponseDTO> addInvestment(
            @RequestBody @Valid InvestmentRequestDTO requestDTO, @PathVariable Long id, Principal principal) {

        String email = principal.getName();
        InvestmentResponseDTO responseDTO = investmentService.insert(requestDTO, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @GetMapping("/{walletId}/investments")
    public ResponseEntity<List<InvestmentResponseDTO>> findAllInvestments(@PathVariable Long walletId, Principal principal) {
        String email = principal.getName();
        List<InvestmentResponseDTO> investments = investmentService.findAllInvest(email, walletId);

        return ResponseEntity.ok(investments);
    }

    @DeleteMapping("/investment/{id}")
    public ResponseEntity<Void> deleteInvest(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        investmentService.delete(email, id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("{walletId}/balance")
    public ResponseEntity<WalletInsertResponseDTO> updateBalance(
            @RequestBody @Valid WalletReqBalanceUpadateDTO request,
            @PathVariable Long walletId, Principal principal) {

        String email = principal.getName();
        WalletInsertResponseDTO response = walletService.updateBalance(email, walletId, request.balance());

        return ResponseEntity.ok(response);
    }

}
