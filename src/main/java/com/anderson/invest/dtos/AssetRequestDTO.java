package com.anderson.invest.dtos;

import com.anderson.invest.entities.Type;
import jakarta.validation.constraints.*;

public record AssetRequestDTO(
        @NotBlank(message = "Ticker é obrigatorio")
        String ticker,
        @NotNull(message = "Tipo é obrigatório")
        Type type,

        @NotNull(message = "Valor obrigatório")
        @Positive(message = "Valor invalido")
        @Min(value = 1, message = "Valor minimo é 1")
        @Max(value = 5, message = "Valor maximo é 5")
        Integer riskLevel
) {
}
