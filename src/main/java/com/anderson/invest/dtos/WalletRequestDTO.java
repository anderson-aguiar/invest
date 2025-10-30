package com.anderson.invest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WalletRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @PositiveOrZero(message = "Valor precisa ser positivo ou zero")
        @NotNull(message = "Saldo obrigatório")
        BigDecimal balance

) {}
