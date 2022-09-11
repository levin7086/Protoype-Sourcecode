import {Injectable} from '@angular/core';
import {Order} from "../model/Order";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {OrderInfo} from "../model/OrderInfo";

@Injectable({
  providedIn: 'root'
})
export class OrderServiceService {

  private ordersUrl = 'http://localhost:8081/orders/';

  constructor(private http: HttpClient) {
  }

  placeOrder(order: Order) {
    let orderPositions: any[] = [];
    order.shoppingCart$.pipe(map(value => {
      return value.items.map(item => {
        return {
          articleNumber: item.productID,
          quantity: item.productQuantity
        }
      })
    })).subscribe(value => {
      this.http.post("http://localhost:8081/order/create", {
        userId: order.userId,
        shippingAddress: order.shippingAddress,
        billingAddress: order.billingAddress,
        orderPositions: value,
        paymentMethod: order.paymentMethod?.method,
        shippingMethod: order.shippingMethod?.method
      }).subscribe();
    })
  }

  getOrders(userId: string): Observable<OrderInfo[]>{
    const url = `${this.ordersUrl}${userId}`
    return this.http.get<OrderInfo[]>(url);
  }

}
