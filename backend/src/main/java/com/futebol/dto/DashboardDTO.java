package com.futebol.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        List<PlayerPaymentStatusDTO> currentMonthPayments,
        int currentMonth,
        int currentYear
) {}
