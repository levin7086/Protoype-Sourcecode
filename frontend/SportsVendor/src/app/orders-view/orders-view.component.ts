import {Component, OnInit} from '@angular/core';
import {OrderServiceService} from "../../shared/services/order-service.service";
import {filter, map, Observable, switchMap, take} from "rxjs";
import {OrderInfo, OrderPosition} from "../../shared/model/OrderInfo";
import {currencyFormatter} from "../common_utils";
import {CatalogService} from "../../shared/services/catalog.service";

@Component({
  selector: 'app-orders-view',
  templateUrl: './orders-view.component.html',
  styleUrls: ['./orders-view.component.css']
})
export class OrdersViewComponent implements OnInit {

  orders$: Observable<OrderInfo[]>;
  orders: OrderInfo[];

  constructor(private orderService: OrderServiceService, private catalogService: CatalogService) {
  }

  ngOnInit(): void {
    this.orders$ = this.orderService.getOrders('1').pipe(
      take(1),
      switchMap(ordersResponse => {
        let orderIds = ordersResponse
          .flatMap(info => info.orderPositions)
          .map(position => position.articleNumber)
          .filter(articleNumber => articleNumber.startsWith('P#'))
        return this.catalogService.getProductsByIds([...new Set(orderIds)])
          .pipe(
            take(1),
            map(products => {
              return ordersResponse.map(order => {
                order.orderPositions = order.orderPositions.map(position => {
                  position.articleName = products
                    .find(product => product.articleNumber === position.articleNumber)?.name
                  if(position.articleName == null){
                    position.articleName = position.articleNumber
                  }
                  return position;
                })
                return order;
              });
            })
          )
      })
    )

    this.orders$.subscribe(orderResponse => {
      this.orders = orderResponse.reverse()
    })

  }

  getPrice(position: OrderPosition) {
    return currencyFormatter(position.price, "EUR ")
  }

  getTotal(orderInfo: OrderInfo) {
    return currencyFormatter(orderInfo.totalPrice, 'EUR ')
  }

}
