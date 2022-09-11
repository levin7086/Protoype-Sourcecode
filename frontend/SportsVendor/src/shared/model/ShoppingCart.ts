import {ShoppingCartItem} from "./ShoppingCartItem";

export class ShoppingCart{
  userId: number;
  items: ShoppingCartItem[] = [];
}

export function getTotalPrice(shoppingCart: ShoppingCart): number{
  let total = shoppingCart.items.reduce<number>((previousValue, currentValue) => {
    return previousValue + currentValue.product.price * currentValue.productQuantity;
  },0)
  return total;
}
