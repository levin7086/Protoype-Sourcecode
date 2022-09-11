import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {ShoppingCartService} from "../../../shared/services/shopping-cart.service";
import {BehaviorSubject, map, Observable, Subject, take} from "rxjs";
import {getTotalPrice, ShoppingCart} from "../../../shared/model/ShoppingCart";
import {getPrice, ShoppingCartItem} from "../../../shared/model/ShoppingCartItem";
import {Order} from "../../../shared/model/Order";
import {currencyFormatter} from "../../common_utils";
import {OrderRow} from "../order-overview-table/order-overview-table.component";
import {paymentMethods} from "../../../shared/model/PaymentMethod";

@Component({
  selector: 'app-payment-wizard-step',
  templateUrl: './payment-wizard-step.component.html',
  styleUrls: ['./payment-wizard-step.component.css']
})
export class PaymentWizardStepComponent implements OnInit {

  @Input() paymentForm!: FormGroup;
  order!: Order;
  @Input() order$: Subject<Order>;
  @Output() next = new EventEmitter<boolean>();
  invalid: boolean;
  total: string;

  orderPositions: ShoppingCartItem[];

  constructor() {
  }

  ngOnInit(): void {
    this.order$.subscribe(value => this.order = value);
    this.order.shoppingCart$.subscribe((value: ShoppingCart) => {
      this.orderPositions = value.items;
    })
    this.order.shoppingCart$.pipe(take(1)).subscribe(value => {
      this.total = currencyFormatter(getTotalPrice(value), "EUR ");
    })
  }

  onNext() {
    if (this.paymentForm.valid) {
      this.order.billingAddress = {
        firstName: this.paymentForm.get('firstName')?.value,
        lastName: this.paymentForm.get('lastName')?.value,
        street: this.paymentForm.get('street')?.value,
        houseNumber: this.paymentForm.get('houseNr')?.value,
        postalCode: this.paymentForm.get('postalCode')?.value,
        place: this.paymentForm.get('place')?.value
      }

      this.order.paymentMethod = paymentMethods[this.paymentForm.get('paymentMethod')?.value - 1];
      this.order$.next(this.order);
      return this.next.emit(true);
    }
    if (this.paymentForm.invalid) {
      this.invalid = true;
    }
  }
}
