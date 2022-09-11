import {Component, Input, OnInit} from '@angular/core';
import {Order} from "../../../shared/model/Order";
import {ShoppingCartItem} from "../../../shared/model/ShoppingCartItem";
import {ShoppingCart} from "../../../shared/model/ShoppingCart";
import {Subject} from "rxjs";
import {OrderServiceService} from "../../../shared/services/order-service.service";
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-complete-order-wizard-step',
  templateUrl: './complete-order-wizard-step.component.html',
  styleUrls: ['./complete-order-wizard-step.component.css']
})
export class CompleteOrderWizardStepComponent implements OnInit {

  @Input() formGroup: FormGroup;
  @Input() order$: Subject<Order>;
  order: Order;
  orderPositions: ShoppingCartItem[];


  constructor(private orderService: OrderServiceService,
              private router: Router) { }

  ngOnInit(): void {
    this.order$.subscribe(value => this.order = value);
    this.order.shoppingCart$.subscribe((value: ShoppingCart) => {
      this.orderPositions = value.items;
    })
  }

  saveOrder(){
    this.orderService.placeOrder(this.order)
    setTimeout(() => {
      this.router.navigate(['/orders'])
    }, 1000)
  }

}
