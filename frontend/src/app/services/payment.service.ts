import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaymentItem, PlayerPayments } from '../models/payment.model';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private apiUrl = '/api/payments';

  constructor(private http: HttpClient) {}

  getByYear(year: number): Observable<PlayerPayments[]> {
    return this.http.get<PlayerPayments[]>(`${this.apiUrl}?year=${year}`);
  }

  toggle(id: number): Observable<PaymentItem> {
    return this.http.patch<PaymentItem>(`${this.apiUrl}/${id}/toggle`, {});
  }
}
