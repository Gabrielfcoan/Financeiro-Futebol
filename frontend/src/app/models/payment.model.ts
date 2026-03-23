export interface PaymentItem {
  id: number;
  month: number;
  paid: boolean;
  paidAt: string | null;
  amount: number;
}

export interface PlayerPayments {
  playerId: number;
  playerName: string;
  payments: PaymentItem[];
}
