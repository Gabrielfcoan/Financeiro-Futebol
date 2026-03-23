package com.futebol.repository;

import com.futebol.entity.MonthlyPayment;
import com.futebol.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment, Long> {

    List<MonthlyPayment> findByPlayerAndYearOrderByMonth(Player player, int year);

    List<MonthlyPayment> findByMonthAndYear(int month, int year);

    boolean existsByPlayerAndMonthAndYear(Player player, int month, int year);

    @Query("SELECT COALESCE(SUM(mp.amount), 0) FROM MonthlyPayment mp WHERE mp.paid = true AND mp.year = :year")
    BigDecimal sumPaidByYear(int year);
}
