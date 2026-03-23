package com.futebol.service;

import com.futebol.dto.PaymentItemDTO;
import com.futebol.dto.PlayerPaymentsDTO;
import com.futebol.entity.MonthlyPayment;
import com.futebol.entity.Player;
import com.futebol.repository.MonthlyPaymentRepository;
import com.futebol.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final MonthlyPaymentRepository paymentRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    public PaymentService(MonthlyPaymentRepository paymentRepository,
                          PlayerRepository playerRepository,
                          PlayerService playerService) {
        this.paymentRepository = paymentRepository;
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    @Transactional
    public List<PlayerPaymentsDTO> getPaymentsByYear(int year) {
        List<Player> players = playerRepository.findByActiveTrueOrderByName();
        List<PlayerPaymentsDTO> result = new ArrayList<>();

        for (Player player : players) {
            playerService.createPaymentsForYear(player, year);
            List<MonthlyPayment> payments = paymentRepository.findByPlayerAndYearOrderByMonth(player, year);

            List<PaymentItemDTO> items = payments.stream()
                    .map(p -> new PaymentItemDTO(p.getId(), p.getMonth(), p.isPaid(), p.getPaidAt(), p.getAmount()))
                    .toList();

            result.add(new PlayerPaymentsDTO(player.getId(), player.getName(), items));
        }
        return result;
    }

    @Transactional
    public PaymentItemDTO togglePayment(Long id) {
        MonthlyPayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        payment.setPaid(!payment.isPaid());
        payment.setPaidAt(payment.isPaid() ? LocalDate.now() : null);
        MonthlyPayment saved = paymentRepository.save(payment);
        return new PaymentItemDTO(saved.getId(), saved.getMonth(), saved.isPaid(), saved.getPaidAt(), saved.getAmount());
    }
}
