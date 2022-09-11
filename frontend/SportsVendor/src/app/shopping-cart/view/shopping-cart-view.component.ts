import {Component, OnInit} from '@angular/core';
import {ShoppingCartService} from "../../../shared/services/shopping-cart.service";
import {getPrice, ShoppingCartItem} from "../../../shared/model/ShoppingCartItem";
import {ColDef, ColumnApi, GridApi, GridReadyEvent, ValueGetterParams} from "ag-grid-community";
import {currencyFormatter} from "../../common_utils";
import {map, Observable, Subject, take, tap} from "rxjs";
import {CatalogService} from "../../../shared/services/catalog.service";
import {sum} from "ag-grid-community/dist/lib/utils/number";
import {getTotalPrice, ShoppingCart} from "../../../shared/model/ShoppingCart";
import {Product} from "../../../shared/model/Product";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart-view.component.html',
  styleUrls: ['./shopping-cart-view.component.css']
})
export class ShoppingCartViewComponent implements OnInit {

  columnDefs: ColDef[] = [
    {field: 'productID'},
    {field: 'product.name'},
    {field: 'productQuantity'},
    {
      field: 'price',
      valueGetter: priceValueGetter,
    },
  ]

  rowData: Observable<any[]>;
  shoppingCart$: Observable<ShoppingCart>;
  total: string = 'could not compute';
  frameworkComponents: any
  shoppingCartService: ShoppingCartService;
  empty = true;
  gridApi: GridApi;
  type: string;

  constructor(shoppingCartService: ShoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  ngOnInit(): void {
    this.shoppingCart$ = this.shoppingCartService.getShoppingCart(1);
    this.rowData = this.shoppingCart$.pipe(
      map((values) => values.items));
    this.shoppingCart$.pipe(
      take(1)
    ).subscribe(value => {
      this.empty = value.items.length == 0;
      this.total = currencyFormatter(getTotalPrice(value), "EUR ");
    })
  }

  onRecommendationAdded(product: Product) {
    this.shoppingCartService.addItem('1', product.articleNumber)
    let cartItem: ShoppingCartItem = new ShoppingCartItem();
    cartItem.product = product
    cartItem.productQuantity =1
    cartItem.productID = product.articleNumber
    this.gridApi.applyTransaction({add: [cartItem]})
  }

  onGridReady(params: GridReadyEvent) {
    this.gridApi = params.api;
  }

}

const priceValueGetter = function (params: ValueGetterParams) {
  const item: ShoppingCartItem = params.data as ShoppingCartItem;
  return currencyFormatter(getPrice(item), 'EUR ');
};
