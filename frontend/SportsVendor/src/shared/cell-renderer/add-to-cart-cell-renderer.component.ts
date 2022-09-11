import {Component, OnDestroy, OnInit} from '@angular/core';
import {ICellRendererAngularComp} from "ag-grid-angular";
import {ICellRendererParams} from "ag-grid-community";

@Component({
  selector: 'app-add-to-cart-cell-renderer',
  template: '<button mat-icon-button style="color: #FF7F11" (click)="btnClickedHandler($event)"><mat-icon>add_shopping_cart</mat-icon></button>'
})
export class AddToCartCellRendererComponent implements ICellRendererAngularComp, OnDestroy {

  private params: any;

  constructor() { }

  btnClickedHandler($event: MouseEvent) {
    if (this.params.onClick instanceof Function) {
      // put anything into params u want pass into parents component
      const params = {
        event: $event,
        rowData: this.params.node.data
      }
      this.params.onClick(params);
    }
  }

  agInit(params: ICellRendererParams): void {
    this.params = params;
  }

  ngOnDestroy(): void {
  }

  refresh(params: ICellRendererParams): boolean {
    return false;
  }
}
