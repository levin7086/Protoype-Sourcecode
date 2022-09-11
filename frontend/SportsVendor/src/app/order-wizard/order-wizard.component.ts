import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {ShippingWizardStepComponent} from "./shipping-wizard-step/shipping-wizard-step.component";
import {MatStepper} from "@angular/material/stepper";
import {Order} from "../../shared/model/Order";
import {ShoppingCart} from "../../shared/model/ShoppingCart";
import {ShoppingCartService} from "../../shared/services/shopping-cart.service";

@Component({
  selector: 'app-order-wizard',
  templateUrl: './order-wizard.component.html',
  styleUrls: ['./order-wizard.component.css']
})
export class OrderWizardComponent implements OnInit {

  orderForm!: FormGroup;
  order: Order;
  order$: Subject<Order>;
  shoppingCartService: ShoppingCartService;

  constructor(private fb: FormBuilder, shoppingCartService: ShoppingCartService) {
    this.shoppingCartService = shoppingCartService;
    this.order = new Order;
    this.order$ = new BehaviorSubject<Order>(this.order);
  }

  ngOnInit(): void {
    this.order.shoppingCart$ = this.shoppingCartService.getShoppingCart(1);
    this.orderForm = this.fb.group({
      shipping: this.shippingForm(),
      payment: this.paymentForm()
    });
  }

  shippingForm() {
    return this.fb.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      street: [null, [Validators.required]],
      houseNr: [null, [Validators.required]],
      postalCode: [null, [Validators.required]],
      place: [null, [Validators.required]],
      delivery: [1, [Validators.required]]
    });
  }

  paymentForm() {
    return this.fb.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      street: [null, [Validators.required]],
      houseNr: [null, [Validators.required]],
      postalCode: [null, [Validators.required]],
      place: [null, [Validators.required]],
      paymentMethod: [1, [Validators.required]]
    });
  }

  get shippingStep(){
    return this.orderForm.get('shipping') as FormGroup;
  }

  get paymentStep(){
    return this.orderForm.get('payment') as FormGroup;
  }

  next(stepper: MatStepper){
    stepper.next();
  }

}
