import {Address} from "./Address";

export interface OrderInfo{
  userId: string,
  orderNumber: string,
  shippingAddress: Address,
  billingAddress: Address,
  orderPositions: OrderPosition[],
  paymentMethod: string,
  shippingMethod: string,
  orderStatus: string
  totalPrice: number
}

export interface OrderPosition {
  articleNumber: string,
  articleName?: string,
  price: number,
  quantity: number
}
