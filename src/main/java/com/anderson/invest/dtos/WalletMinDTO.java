package com.anderson.invest.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WalletMinDTO(
        String name,
        LocalDate createdAt,
        BigDecimal balance
) {}
