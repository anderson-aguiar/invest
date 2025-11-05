package com.anderson.invest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record InvestmentRequestDTO(
        @NotNull(message = "Quantidade obrigatória")
        @Positive(message = "Informar valor positivo")
        double quantity,

        @NotNull(message = "Preço obrigatório")
        @Positive(message = "Informar valor positivo")
        BigDecimal purchasePrice,

        @NotNull(message = "Id do ativo é obrigatório")
        Long assetId,

        @NotNull(message = "Id da carteira é obrigatório")
        Long walletId

) {
}
