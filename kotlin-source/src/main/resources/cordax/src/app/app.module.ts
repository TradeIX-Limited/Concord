import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

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
  MatProgressSpinnerModule
} from '@angular/material';

import { RootComponent } from './root/root.component';

import 'hammerjs';
import { BuyerDashboardComponent } from './buyer-dashboard/buyer-dashboard.component';
import { NewPurchaseOrderDialogComponent } from './buyer-dashboard/new-purchase-order-dialog/new-purchase-order-dialog.component';

@NgModule({
  declarations: [
    RootComponent,
    BuyerDashboardComponent,
    NewPurchaseOrderDialogComponent
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
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [RootComponent],
  entryComponents: [NewPurchaseOrderDialogComponent]
})
export class AppModule { }
