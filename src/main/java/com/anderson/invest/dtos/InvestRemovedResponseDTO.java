package com.anderson.invest.dtos;

import java.math.BigDecimal;

public record InvestRemovedResponseDTO(
        String email,
        BigDecimal investmentBalance,
        String assetTicker,
        String walletname,
        BigDecimal actualWalletBalance
) {}
