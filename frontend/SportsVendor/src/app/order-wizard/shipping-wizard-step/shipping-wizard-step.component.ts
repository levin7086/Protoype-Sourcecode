import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subject} from "rxjs";
import {Order} from "../../../shared/model/Order";
import {Address} from "../../../shared/model/Address";
import {shippingMethods} from "../../../shared/model/ShippingMethod";

@Component({
  selector: 'app-shipping-wizard-step',
  templateUrl: './shipping-wizard-step.component.html',
  styleUrls: ['./shipping-wizard-step.component.css'],
})
export class ShippingWizardStepComponent implements OnInit {

  @Input() shippingForm!: FormGroup;
  @Input() order$: Subject<Order>;
  order: Order;
  @Output() next = new EventEmitter<boolean>();
  invalid: boolean;

  constructor(private _fb: FormBuilder) {

  }

  ngOnInit() {
    this.order$.subscribe(value => this.order = value);
  }

  onNext() {
    if (this.shippingForm.valid) {
      let address: Address = {
        firstName: this.shippingForm.get('firstName')?.value,
        lastName: this.shippingForm.get('lastName')?.value,
        street: this.shippingForm.get('street')?.value,
        houseNumber: this.shippingForm.get('houseNr')?.value,
        postalCode: this.shippingForm.get('postalCode')?.value,
        place: this.shippingForm.get('place')?.value
      }
      this.order.shippingAddress = address;
      this.order.shippingMethod = shippingMethods[this.shippingForm.get('delivery')?.value - 1];
      this.order$.next(this.order);
      return this.next.emit(true);
    }
    if (this.shippingForm.invalid) {
      this.invalid = true;
    }
  }

}
