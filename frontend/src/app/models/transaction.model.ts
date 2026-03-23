export type TransactionType = 'INCOME' | 'EXPENSE';

export interface Transaction {
  id?: number;
  description: string;
  amount: number;
  type: TransactionType;
  category: string;
  date: string;
}
