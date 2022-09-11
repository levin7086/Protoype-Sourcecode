import {Product} from "./Product";

export class ShoppingCartItem{
  productID: string;
  product!: Product;
  productQuantity: number;

}

export function getPrice(item: ShoppingCartItem){
  return item.productQuantity * item.product.price;
}
