package com.anderson.invest.mappers;

import com.anderson.invest.dtos.WalletInsertResponseDTO;
import com.anderson.invest.dtos.WalletRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.entities.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletMapper {

    public Wallet toEntity(WalletRequestDTO requestDTO, User user) {

        if (requestDTO == null) return null;

        Wallet wallet = new Wallet();
        wallet.setName(requestDTO.name());
        wallet.setBalance(requestDTO.balance());
        wallet.setUser(user);
        user.addWallet(wallet);
        return wallet;
    }

    public WalletInsertResponseDTO toInsertDTO(Wallet wallet) {

        return new WalletInsertResponseDTO(wallet.getId(), wallet.getName(), wallet.getBalance());
    }
}
