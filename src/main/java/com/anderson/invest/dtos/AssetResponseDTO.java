package com.anderson.invest.dtos;

import com.anderson.invest.entities.Type;
import jakarta.validation.constraints.*;

public record AssetResponseDTO(

        Long id,
        String ticker,
        Type type,
        Integer riskLevel
) {}
