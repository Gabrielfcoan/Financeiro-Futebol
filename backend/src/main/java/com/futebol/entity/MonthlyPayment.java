package com.futebol.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "monthly_payments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"player_id", "month", "year"}))
@Data
@NoArgsConstructor
public class MonthlyPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int year;

    private boolean paid = false;

    private LocalDate paidAt;

    @Column(nullable = false)
    private BigDecimal amount;
}
