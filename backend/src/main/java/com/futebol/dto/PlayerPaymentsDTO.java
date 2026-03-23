package com.futebol.dto;

import java.util.List;

public record PlayerPaymentsDTO(
        Long playerId,
        String playerName,
        List<PaymentItemDTO> payments
) {}
