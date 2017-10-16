import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatButtonModule,
  MatMenuModule,
  MatListModule,
  MatCardModule,
  MatDialogModule,
  MatSelectModule,
  MatOptionModule,
  MatInputModule,
  MatFormFieldModule,
  MatProgressSpinnerModule,
  MatChipsModule
} from '@angular/material';

import { RootComponent } from './root/root.component';

import 'hammerjs';
import { BuyerDashboardComponent } from './buyer-dashboard/buyer-dashboard.component';
import { NewPurchaseOrderDialogComponent } from './buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component';
import { SupplierDashboardComponent } from './supplier-dashboard/supplier-dashboard.component';
import { FunderDashboardComponent } from './funder-dashboard/funder-dashboard.component';
import { MainComponent } from './main/main.component';

import { NodeService } from '../api/domain/services/node.service';
import { TradeAssetService } from '../api/domain/services/trade-asset.service';

const routes = [
  { path: '', component: MainComponent },
  { path: 'buyer', component: BuyerDashboardComponent },
  { path: 'supplier', component: SupplierDashboardComponent },
  { path: 'funder', component: FunderDashboardComponent }
];

@NgModule({
  declarations: [
    RootComponent,
    BuyerDashboardComponent,
    NewPurchaseOrderDialogComponent,
    SupplierDashboardComponent,
    FunderDashboardComponent,
    MainComponent
  ],
  imports: [
    FlexLayoutModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatCardModule,
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    RouterModule.forRoot(routes, { enableTracing: false })
  ],
  providers: [NodeService, TradeAssetService],
  bootstrap: [RootComponent],
  entryComponents: [NewPurchaseOrderDialogComponent]
})
export class AppModule { }
