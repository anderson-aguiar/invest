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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final InvestmentMapper investmentMapper;
    private final WalletRepository walletRepository;

    public InvestmentService(InvestmentRepository investmentRepository, UserRepository userRepository, AssetRepository assetRepository, InvestmentMapper investmentMapper, WalletRepository walletRepository) {
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.investmentMapper = investmentMapper;
        this.walletRepository = walletRepository;
    }


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
    @Transactional(readOnly = true)
    public List<InvestmentResponseDTO> findAllInvest(String email, Long walletId){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isEmpty()) {
            throw new EntityNotFoundException("Carteira não encontrada");
        }
       if(!optionalWallet.get().getUser().equals(user)){
           throw new SecurityException("Acesso negado: esta carteira não pertence ao usuário especificado.");
       }

        return optionalWallet.get()
                .getInvestments()
                .stream()
                .map(investmentMapper::toResponseDTO)
                .toList();
    }
}
