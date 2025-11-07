package com.anderson.invest.dtos;

import java.math.BigDecimal;

public record UserSummaryResponseDTO(
        Long userId,
        BigDecimal totalInvestment,
        int totalAsset,
        double riskAverage
) {
}
