package com.anderson.invest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WalletReqBalanceUpadateDTO(
        @PositiveOrZero(message = "Valor precisa ser positivo ou zero")
        @NotNull(message = "Saldo obrigatório")
        BigDecimal balance
) {}
