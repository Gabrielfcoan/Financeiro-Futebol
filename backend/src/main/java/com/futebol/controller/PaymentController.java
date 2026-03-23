package com.futebol.controller;

import com.futebol.dto.PaymentItemDTO;
import com.futebol.dto.PlayerPaymentsDTO;
import com.futebol.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PlayerPaymentsDTO> getByYear(@RequestParam(required = false) Integer year) {
        int y = (year != null) ? year : LocalDate.now().getYear();
        return paymentService.getPaymentsByYear(y);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<PaymentItemDTO> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.togglePayment(id));
    }
}
