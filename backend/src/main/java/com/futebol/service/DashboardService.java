package com.futebol.service;

import com.futebol.dto.DashboardDTO;
import com.futebol.dto.PlayerPaymentStatusDTO;
import com.futebol.entity.MonthlyPayment;
import com.futebol.entity.TransactionType;
import com.futebol.repository.MonthlyPaymentRepository;
import com.futebol.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final MonthlyPaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(MonthlyPaymentRepository paymentRepository,
                            TransactionRepository transactionRepository) {
        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public DashboardDTO getDashboard() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        LocalDate startOfYear = LocalDate.of(currentYear, 1, 1);
        LocalDate endOfYear = LocalDate.of(currentYear, 12, 31);

        BigDecimal paidFees = paymentRepository.sumPaidByYear(currentYear);
        BigDecimal incomeTransactions = transactionRepository.sumByTypeAndDateBetween(
                TransactionType.INCOME, startOfYear, endOfYear);
        BigDecimal expenseTransactions = transactionRepository.sumByTypeAndDateBetween(
                TransactionType.EXPENSE, startOfYear, endOfYear);

        BigDecimal totalIncome = paidFees.add(incomeTransactions);
        BigDecimal totalExpense = expenseTransactions;
        BigDecimal balance = totalIncome.subtract(totalExpense);

        List<MonthlyPayment> currentMonthPayments = paymentRepository.findByMonthAndYear(currentMonth, currentYear);
        List<PlayerPaymentStatusDTO> paymentStatus = currentMonthPayments.stream()
                .map(p -> new PlayerPaymentStatusDTO(
                        p.getPlayer().getName(),
                        p.isPaid(),
                        p.getAmount(),
                        p.getPaidAt()
                ))
                .sorted((a, b) -> Boolean.compare(a.paid(), b.paid()))
                .toList();

        return new DashboardDTO(totalIncome, totalExpense, balance, paymentStatus, currentMonth, currentYear);
    }
}
