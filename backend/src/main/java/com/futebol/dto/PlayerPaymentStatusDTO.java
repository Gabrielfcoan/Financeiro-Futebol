package com.futebol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PlayerPaymentStatusDTO(
        String playerName,
        boolean paid,
        BigDecimal amount,
        LocalDate paidAt
) {}
