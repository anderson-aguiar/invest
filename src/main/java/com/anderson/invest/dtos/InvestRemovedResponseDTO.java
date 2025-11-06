package com.anderson.invest.dtos;

import java.math.BigDecimal;

public record InvestRemovedResponseDTO(
        String email,
        BigDecimal balance,
        String assetTicker,
        String walletname,
        BigDecimal totalWalletBalance
) {}
