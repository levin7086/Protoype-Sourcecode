import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {ShoppingCartViewComponent} from "./shopping-cart/view/shopping-cart-view.component";
import {AppComponent} from "./app.component";
import {BrowseViewComponent} from "./browse-view/browse-view.component";
import {OrderWizardComponent} from "./order-wizard/order-wizard.component";
import {OrdersViewComponent} from "./orders-view/orders-view.component";

const routes: Routes = [
  {path: "browse", component: BrowseViewComponent},
  {path: "cart", component: ShoppingCartViewComponent},
  {path: "order", component: OrderWizardComponent},
  {path: "orders", component: OrdersViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
