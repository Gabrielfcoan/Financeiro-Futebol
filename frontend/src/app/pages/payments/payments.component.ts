import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentService } from '../../services/payment.service';
import { PlayerPayments, PaymentItem } from '../../models/payment.model';

const MONTHS = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
  'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];

@Component({
  selector: 'app-payments',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './payments.component.html'
})
export class PaymentsComponent implements OnInit {
  playerPayments: PlayerPayments[] = [];
  loading = false;
  selectedYear: number = new Date().getFullYear();
  months = MONTHS;
  years: number[] = [];
  toggling = new Set<number>();

  constructor(private paymentService: PaymentService) {
    const currentYear = new Date().getFullYear();
    for (let y = currentYear - 2; y <= currentYear + 1; y++) {
      this.years.push(y);
    }
  }

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.loading = true;
    this.paymentService.getByYear(this.selectedYear).subscribe({
      next: data => { this.playerPayments = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  togglePayment(player: PlayerPayments, payment: PaymentItem): void {
    if (this.toggling.has(payment.id)) return;
    this.toggling.add(payment.id);
    this.paymentService.toggle(payment.id).subscribe({
      next: updated => {
        payment.paid = updated.paid;
        payment.paidAt = updated.paidAt;
        this.toggling.delete(payment.id);
      },
      error: () => this.toggling.delete(payment.id)
    });
  }

  getPaymentForMonth(player: PlayerPayments, month: number): PaymentItem | undefined {
    return player.payments.find(p => p.month === month);
  }

  totalPaidByPlayer(player: PlayerPayments): number {
    return player.payments.filter(p => p.paid).reduce((sum, p) => sum + p.amount, 0);
  }

  totalPaidAll(): number {
    return this.playerPayments.reduce((sum, p) => sum + this.totalPaidByPlayer(p), 0);
  }
}
