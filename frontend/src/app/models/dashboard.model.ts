export interface PlayerPaymentStatus {
  playerName: string;
  paid: boolean;
  amount: number;
  paidAt: string | null;
}

export interface Dashboard {
  totalIncome: number;
  totalExpense: number;
  balance: number;
  currentMonthPayments: PlayerPaymentStatus[];
  currentMonth: number;
  currentYear: number;
}
