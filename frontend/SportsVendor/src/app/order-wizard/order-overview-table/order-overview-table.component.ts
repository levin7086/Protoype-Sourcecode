import {Component, Input, OnInit} from '@angular/core';
import {forkJoin, map, mergeMap, Observable, Subject, switchMap} from "rxjs";
import {getTotalPrice, ShoppingCart} from "../../../shared/model/ShoppingCart";
import {currencyFormatter} from "../../common_utils";
import {getPrice} from "../../../shared/model/ShoppingCartItem";
import {Order} from "../../../shared/model/Order";
import {OrderServiceService} from "../../../shared/services/order-service.service";

export interface OrderRow {
  name: string;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-order-overview-table',
  templateUrl: './order-overview-table.component.html',
  styleUrls: ['./order-overview-table.component.css']
})
export class OrderOverviewTableComponent implements OnInit {

  @Input() order$: Subject<Order>;
  order: Order;
  data: OrderRow[] = [];
  displayedColumns: string[] = ['name', 'quantity', 'price'];
  total: string;

  constructor() {
  }

  ngOnInit(): void {
    this.order$.pipe(
      switchMap(value => {
        let rows: OrderRow[] = [];
        if (value.shippingMethod !== undefined) {
          rows = rows.concat(
            {
              name: value.shippingMethod.name,
              quantity: 1,
              price: value.shippingMethod.price
            }
          );
        }
        return value.shoppingCart$.pipe(
          map(shoppingCart => {
            return rows.concat(shoppingCart.items.map(item => {
                let orderRow: OrderRow = {
                  name: item.product.name,
                  quantity: item.productQuantity,
                  price: getPrice(item)
                };
                return orderRow;
              })
            )
          })
        );
      })).subscribe(value => {
      this.total = currencyFormatter(this.getTotal(), 'EUR ');
      this.data = value
    });
  }

  getOrderRowPrice(row: OrderRow) {
    return currencyFormatter(row.price, 'EUR ')
  }

  getTotal() {
    return this.data
      .map(value => value.price)
      .reduce((previousValue, currentValue) => previousValue + currentValue, 0);
  }
}
