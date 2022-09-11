import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {ShoppingCartItem} from "../model/ShoppingCartItem";
import {
  combineAll,
  combineLatest,
  combineLatestAll,
  concat, flatMap,
  forkJoin,
  map,
  mergeMap,
  Observable,
  of,
  publish, switchMap, take,
  tap
} from "rxjs";
import {ShoppingCart} from "../model/ShoppingCart";
import {combineTransforms} from "@angular/cdk/drag-drop/drag-styling";
import {Product} from "../model/Product";
import {CatalogService} from "./catalog.service";
import {pushAll} from "ag-grid-community/dist/lib/utils/array";


@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  private cartUrl = 'http://localhost:8082/cart';

  constructor(private http: HttpClient, private catalogService: CatalogService) {
  }

  getShoppingCart(userId: number): Observable<ShoppingCart> {
    const url = `${this.cartUrl}/${userId}`;

    return this.http.get<ShoppingCartItem[]>(url)
      .pipe(
        switchMap(response => {
          return this.catalogService.getProductsByIds(response.map(v => v.productID))
            .pipe(
              map(products => {
                let cart: ShoppingCart = new ShoppingCart();
                cart.userId = userId;
                response.forEach(i => {
                  i.product = products.filter(p => p.articleNumber == i.productID)[0]
                  cart.items.push(i);
                })
                return cart;
              }))
        }));
  }

  getItemPrice(cartItem: ShoppingCartItem){
    return cartItem.product.price * cartItem.productQuantity;
  }

  addItem(userId: string, articleNumber: string){
    const headers = new HttpHeaders()
      .append(
        'Content-Type',
        'application/json'
      )
      .append(
        'Content-Lenght',
        '0'
      );
    const params = new HttpParams()
      .append('userId', userId)
      .append('productId', articleNumber);
    this.http.post("http://localhost:8082/cart/add", {}, {
      headers: headers,
      params: params,
    }).subscribe()
  }

}
