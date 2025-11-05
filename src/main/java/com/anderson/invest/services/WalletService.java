package com.anderson.invest.services;

import com.anderson.invest.dtos.WalletInsertResponseDTO;
import com.anderson.invest.dtos.WalletMinDTO;
import com.anderson.invest.dtos.WalletRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.entities.Wallet;
import com.anderson.invest.mappers.WalletMapper;
import com.anderson.invest.repositories.UserRepository;
import com.anderson.invest.repositories.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.walletMapper = walletMapper;
    }

    @Transactional
    public WalletInsertResponseDTO insert(WalletRequestDTO requestDTO, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("Usuário logado não encontrado no banco de dados");
        }
        Wallet wallet = walletMapper.toEntity(requestDTO, user);
        Wallet savedWallet = walletRepository.save(wallet);

        return walletMapper.toInsertDTO(savedWallet);
    }

    @Transactional(readOnly = true)
    public List<WalletMinDTO> findAllWallets(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("Usuário logado não encontrado no banco de dados");
        }
        List<WalletMinDTO> wallets = new ArrayList<>();
        user.getWallets().forEach(w -> wallets.add(new WalletMinDTO(w.getName(), w.getCreatedAt().toLocalDate(), w.getBalance())));
        return wallets;
    }
}
