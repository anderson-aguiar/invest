package com.anderson.invest.services;

import com.anderson.invest.dtos.InvestmentRequestDTO;
import com.anderson.invest.dtos.InvestmentResponseDTO;
import com.anderson.invest.entities.Asset;
import com.anderson.invest.entities.Investment;
import com.anderson.invest.entities.User;
import com.anderson.invest.entities.Wallet;
import com.anderson.invest.mappers.InvestmentMapper;
import com.anderson.invest.repositories.AssetRepository;
import com.anderson.invest.repositories.InvestmentRepository;
import com.anderson.invest.repositories.UserRepository;
import com.anderson.invest.repositories.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private InvestmentMapper investmentMapper;
    @Autowired
    private WalletRepository walletRepository;


    @Transactional
    public InvestmentResponseDTO insert(InvestmentRequestDTO requestDTO, String email) {
        User user = userRepository.findByEmail(email);
        Optional<Asset> optionalAsset = assetRepository.findById(requestDTO.assetId());
        Optional<Wallet> optionalWallet = walletRepository.findById(requestDTO.walletId());
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        if (optionalAsset.isEmpty()) {
            throw new EntityNotFoundException("Ativo não encontrado");
        }
        if (optionalWallet.isEmpty()) {
            throw new EntityNotFoundException("Carteira não encontrada");
        }
        BigDecimal totalInvest = requestDTO.purchasePrice().multiply(BigDecimal.valueOf(requestDTO.quantity()));
        BigDecimal walletBalance = optionalWallet.get().getBalance();

        if (walletBalance.compareTo(totalInvest) < 0) {
            throw new RuntimeException("Saldo infuciente");
        }else{
            walletBalance = walletBalance.subtract(totalInvest);
            optionalWallet.get().setBalance(walletBalance);
        }

        Investment investment =
                investmentMapper.toEntity(requestDTO, optionalAsset.get(), optionalWallet.get());
        Investment saveInvest = investmentRepository.save(investment);
        optionalAsset.get().addInvestments(saveInvest);
        optionalWallet.get().addInvestments(saveInvest);

        return investmentMapper.toResponseDTO(saveInvest);
    }
}
