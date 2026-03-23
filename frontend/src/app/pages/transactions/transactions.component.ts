import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionService } from '../../services/transaction.service';
import { Transaction, TransactionType } from '../../models/transaction.model';

const CATEGORIES = ['Mensalidade', 'Multa', 'Ginásio', 'Comida', 'Material', 'Arbitragem', 'Transporte', 'Outros'];

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions.component.html'
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[] = [];
  loading = false;
  showForm = false;
  categories = CATEGORIES;

  form: Transaction = {
    description: '',
    amount: 0,
    type: 'EXPENSE',
    category: '',
    date: new Date().toISOString().split('T')[0]
  };

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.loading = true;
    this.transactionService.findAll().subscribe({
      next: data => { this.transactions = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  cancelForm(): void {
    this.showForm = false;
    this.form = {
      description: '',
      amount: 0,
      type: 'EXPENSE',
      category: '',
      date: new Date().toISOString().split('T')[0]
    };
  }

  save(): void {
    if (!this.form.description || this.form.amount <= 0 || !this.form.date) return;
    this.transactionService.create(this.form).subscribe({
      next: () => { this.loadTransactions(); this.cancelForm(); },
      error: err => console.error(err)
    });
  }

  delete(id: number): void {
    if (!confirm('Excluir este lançamento?')) return;
    this.transactionService.delete(id).subscribe(() => this.loadTransactions());
  }

  totalIncome(): number {
    return this.transactions.filter(t => t.type === 'INCOME').reduce((s, t) => s + t.amount, 0);
  }

  totalExpense(): number {
    return this.transactions.filter(t => t.type === 'EXPENSE').reduce((s, t) => s + t.amount, 0);
  }
}
