package com.futebol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentItemDTO(
        Long id,
        int month,
        boolean paid,
        LocalDate paidAt,
        BigDecimal amount
) {}
