package com.anderson.invest.mappers;

import com.anderson.invest.dtos.InvestmentRequestDTO;
import com.anderson.invest.dtos.InvestmentResponseDTO;
import com.anderson.invest.entities.Asset;
import com.anderson.invest.entities.Investment;
import com.anderson.invest.entities.Wallet;
import org.springframework.stereotype.Component;

@Component
public class InvestmentMapper {

    public Investment toEntity(InvestmentRequestDTO requestDTO, Asset asset, Wallet wallet){
        if (requestDTO == null) return null;
        Investment investment = new Investment();
        investment.setQuantity(requestDTO.quantity());
        investment.setPurchasePrice(requestDTO.purchasePrice());
        investment.setWallet(wallet);
        investment.setAsset(asset);
        return investment;
    }

    public InvestmentResponseDTO toResponseDTO(Investment saveInvest) {
        return new InvestmentResponseDTO(
                saveInvest.getId(),
                saveInvest.getQuantity(),
                saveInvest.getPurchasePrice(),
                saveInvest.getPuchaseDate(),
                saveInvest.getWallet().getId(),
                saveInvest.getAsset().getTicker());
    }
}
