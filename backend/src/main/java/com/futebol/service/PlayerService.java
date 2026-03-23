package com.futebol.service;

import com.futebol.entity.MonthlyPayment;
import com.futebol.entity.Player;
import com.futebol.repository.MonthlyPaymentRepository;
import com.futebol.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final MonthlyPaymentRepository paymentRepository;

    public PlayerService(PlayerRepository playerRepository, MonthlyPaymentRepository paymentRepository) {
        this.playerRepository = playerRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAllByOrderByNameAsc();
    }

    @Transactional
    public Player create(Player player) {
        Player saved = playerRepository.save(player);
        createPaymentsForYear(saved, LocalDate.now().getYear());
        return saved;
    }

    public void createPaymentsForYear(Player player, int year) {
        for (int month = 1; month <= 12; month++) {
            if (!paymentRepository.existsByPlayerAndMonthAndYear(player, month, year)) {
                MonthlyPayment payment = new MonthlyPayment();
                payment.setPlayer(player);
                payment.setMonth(month);
                payment.setYear(year);
                payment.setAmount(player.getMonthlyFee());
                paymentRepository.save(payment);
            }
        }
    }

    @Transactional
    public Player update(Long id, Player playerData) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        player.setName(playerData.getName());
        player.setMonthlyFee(playerData.getMonthlyFee());
        return playerRepository.save(player);
    }

    @Transactional
    public Player toggleActive(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        player.setActive(!player.isActive());
        return playerRepository.save(player);
    }

    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
