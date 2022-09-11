import {ShoppingCart} from "./ShoppingCart";
import {Address} from "./Address";
import {Observable} from "rxjs";
import {ShippingMethod} from "./ShippingMethod";
import {PaymentMethod} from "./PaymentMethod";

export class Order{

  userId: string = '1';
  shoppingCart$: Observable<ShoppingCart>;
  shippingAddress?: Address;
  shippingMethod?: ShippingMethod;
  billingAddress?: Address;
  paymentMethod?: PaymentMethod;

}
