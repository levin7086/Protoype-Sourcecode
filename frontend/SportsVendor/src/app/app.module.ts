import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from "@angular/common/http";

import { AppComponent } from './app.component';
import { AgGridModule } from 'ag-grid-angular';
import { CategoryViewComponent } from './browse-view/category-view/category-view.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddToCartCellRendererComponent } from '../shared/cell-renderer/add-to-cart-cell-renderer.component';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import { ShoppingCartViewComponent } from './shopping-cart/view/shopping-cart-view.component';
import { AppRoutingModule } from './app-routing.module';
import {RouterModule, Routes} from "@angular/router";
import { BrowseViewComponent } from './browse-view/browse-view.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTabsModule} from "@angular/material/tabs";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import { OrderWizardComponent } from './order-wizard/order-wizard.component';
import { ShippingWizardStepComponent } from './order-wizard/shipping-wizard-step/shipping-wizard-step.component';
import {MatCardModule} from "@angular/material/card";
import { FlexLayoutModule } from "@angular/flex-layout";
import {MatStepperModule} from "@angular/material/stepper";
import { PaymentWizardStepComponent } from './order-wizard/payment-wizard-step/payment-wizard-step.component';
import {MatRadioModule} from "@angular/material/radio";
import {MatDividerModule} from "@angular/material/divider";
import { CompleteOrderWizardStepComponent } from './order-wizard/complete-order-wizard-step/complete-order-wizard-step.component';
import { OrderOverviewTableComponent } from './order-wizard/order-overview-table/order-overview-table.component';
import {MatTableModule} from "@angular/material/table";
import { OrdersViewComponent } from './orders-view/orders-view.component';
import {MatExpansionModule} from "@angular/material/expansion";
import { ProductComponent } from './product/product.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { ComplementaryProductsComponent } from './complementary-products/complementary-products.component';

@NgModule({
  declarations: [AppComponent ,CategoryViewComponent, AddToCartCellRendererComponent, ShoppingCartViewComponent, BrowseViewComponent, OrderWizardComponent, ShippingWizardStepComponent, PaymentWizardStepComponent, CompleteOrderWizardStepComponent, OrderOverviewTableComponent, OrdersViewComponent, ProductComponent, TopBarComponent, ComplementaryProductsComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AgGridModule.withComponents([AddToCartCellRendererComponent]),
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    AppRoutingModule,
    MatToolbarModule,
    MatTabsModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    FlexLayoutModule,
    MatStepperModule,
    MatRadioModule,
    MatDividerModule,
    MatTableModule,
    MatExpansionModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
