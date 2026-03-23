import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { Dashboard } from '../../models/dashboard.model';
import { AuthService } from '../../services/auth.service';

const MONTHS = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
  'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  dashboard: Dashboard | null = null;
  loading = true;
  error = '';

  constructor(private dashboardService: DashboardService, public authService: AuthService) {}

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: data => {
        this.dashboard = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erro ao carregar dashboard.';
        this.loading = false;
      }
    });
  }

  getMonthName(month: number): string {
    return MONTHS[month - 1];
  }

  paidCount(): number {
    return this.dashboard?.currentMonthPayments.filter(p => p.paid).length ?? 0;
  }

  pendingCount(): number {
    return this.dashboard?.currentMonthPayments.filter(p => !p.paid).length ?? 0;
  }
}
