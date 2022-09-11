export interface PaymentMethod {
  method: 'securePay' | 'creditCard';
  name: string;
}

export const paymentMethods: PaymentMethod[] = [
  {method: 'securePay', name: 'Secure pay'},
  {method: "creditCard", name: 'Credit Card'}
]
