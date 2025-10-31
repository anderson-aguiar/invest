package com.anderson.invest.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestmentResponseDTO(
        Long id,
        double quantity,
        BigDecimal purchasePrice,
        LocalDate purchaseDate,
        Long walletId,
        String assetTicker

) {}
