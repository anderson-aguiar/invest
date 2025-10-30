package com.anderson.invest.dtos;

import java.time.LocalDate;

public record WalletMinDTO(
        String name,
        LocalDate createdAt
) {}
