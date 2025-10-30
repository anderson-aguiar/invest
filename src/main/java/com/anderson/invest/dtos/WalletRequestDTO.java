package com.anderson.invest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WalletRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @Positive(message = "Valor precisa ser positivo")
        @NotNull(message = "Saldo obrigatório")
        BigDecimal balance

) {}
