export interface ShippingMethod {
  method: 'standard' | 'nextDay';
  name: string;
  price: number;
}

export const shippingMethods: ShippingMethod[] = [
  {
    method: "standard",
    name: 'Standard Delivery',
    price:  3.00
  },
  {
    method: "nextDay",
    name: 'Next Day Delivery',
    price:  8.00
  }
]
