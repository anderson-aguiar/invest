package com.anderson.invest.dtos;

import java.math.BigDecimal;

public record WalletInsertResponseDTO(
        Long id,
        String name,
        BigDecimal initialBalance
) {}
