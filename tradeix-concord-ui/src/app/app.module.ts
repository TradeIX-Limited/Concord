import "hammerjs";

import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { RouterModule, Routes } from "@angular/router";
import { FlexLayoutModule } from "@angular/flex-layout";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
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
  MatChipsModule,
  MatTabsModule,
  MatPaginatorModule,
  MatProgressBarModule
} from "@angular/material";

import { RootComponent } from "./root/root.component";
import { MainComponent } from "./main/main.component";
import { HistoryComponent } from "./history/history.component";

import { NodeService } from "../api/domain/nodes/node.service";
import { HistoryService } from "api/domain/history/history.service";

import { Mapper } from "api/domain/shared/mapper";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";
import { UniqueIdentifier, CordaX500Name } from "api/domain/shared/corda";
import { PurchaseOrderService } from "api/domain/purchase-orders/purchase-order.service";
import { DeliveryInformationComponent } from './delivery-information/delivery-information.component';

@NgModule({
  declarations: [
    RootComponent,
    MainComponent,
    HistoryComponent,
    DeliveryInformationComponent
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
    MatTabsModule,
    MatPaginatorModule,
    MatProgressBarModule
  ],
  providers: [NodeService, PurchaseOrderService, HistoryService],
  bootstrap: [RootComponent],
  entryComponents: [DeliveryInformationComponent]
})
export class AppModule {
  public constructor() {
    this.initialize();
  }

  private initialize(): void {
    const mapper: Mapper = Mapper.getInstance();

    mapper.createMapConfiguration(Object, PurchaseOrderState, (input: any) => {
      return new PurchaseOrderState(
        new UniqueIdentifier(input.linearId.externalId, input.linearId.id),
        CordaX500Name.parse(input.owner),
        CordaX500Name.parse(input.buyer),
        CordaX500Name.parse(input.supplier),
        CordaX500Name.parse(input.conductor),
        input.reference,
        input.amount,
        new Date(input.created * 1000),
        new Date(input.earliestShipment * 1000),
        new Date(input.latestShipment * 1000),
        input.portOfShipment,
        input.descriptionOfGoods,
        input.deliveryTerms);
    });
  }
}
