import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../shared/model/Category";
import {ColDef} from "ag-grid-community";
import {currencyFormatter} from "../../common_utils";
import {Observable} from "rxjs";
import {AddToCartCellRendererComponent} from "../../../shared/cell-renderer/add-to-cart-cell-renderer.component";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {ShoppingCartService} from "../../../shared/services/shopping-cart.service";

@Component({
  selector: 'app-category-view',
  templateUrl: './category-view.component.html',
  styleUrls: ['./category-view.component.css']
})
export class CategoryViewComponent implements OnInit {

  @Input('category') category: Category;
  @Input('recommended') recommended = false
  rowData: Observable<any[]>;
  frameworkComponents: any
  columnDefs: ColDef[] = [
    {
      field: '',
      width: 75,
      cellRenderer: 'addToCartCellRendererComponent',
      cellRendererParams: {
        onClick: this.onClick.bind(this),
        label: 'clickedAddToCart'
      }
    },
    {field: 'price', width: 120, valueFormatter: params => currencyFormatter(params.value, 'EUR ')},
    {field: 'name'},
    {field: 'description', minWidth: 500}
  ];

  constructor(private http: HttpClient, private shoppingCartService: ShoppingCartService) {
    this.frameworkComponents = {
      addToCartCellRendererComponent: AddToCartCellRendererComponent
    }
  }

  ngOnInit(): void {
    this.rowData = this.http.get<any[]>(`http://localhost:8080/category/${this.category.name}`);
  }

  onClick(e: any){
    this.shoppingCartService.addItem('1', e.rowData.articleNumber)
  }

}
