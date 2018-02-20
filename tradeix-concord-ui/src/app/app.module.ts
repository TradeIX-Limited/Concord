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

import { NodeService } from "../api/domain/nodes/node.service";
import { HistoryService } from "api/domain/history/history.service";

import { Mapper } from "api/domain/shared/mapper";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";
import { UniqueIdentifier, CordaX500Name } from "api/domain/shared/corda";
import { PurchaseOrderService } from "api/domain/purchase-orders/purchase-order.service";
import { InvoicesService } from "api/domain/invoices/invoices.service";
import { DeliveryInformationComponent } from "./delivery-information/delivery-information.component";
import { PurchaseOrderTableComponent } from "./purchase-order-table/purchase-order-table.component";
import { InvoiceTableComponent } from "./invoice-table/invoice-table.component";
import { InvoiceState } from "api/domain/invoices/invoice-state";
import { InvoiceInformationComponent } from "./invoice-information/invoice-information.component";
import { PurchaseOrderHistoryComponent } from "./purchase-order-history/purchase-order-history.component";
import { InvoiceHistoryComponent } from "./invoice-history/invoice-history.component";

@NgModule({
  declarations: [
    RootComponent,
    MainComponent,
    DeliveryInformationComponent,
    PurchaseOrderTableComponent,
    InvoiceTableComponent,
    InvoiceInformationComponent,
    PurchaseOrderHistoryComponent,
    InvoiceHistoryComponent
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
  providers: [NodeService, PurchaseOrderService, InvoicesService, HistoryService],
  bootstrap: [RootComponent],
  entryComponents: [DeliveryInformationComponent, InvoiceInformationComponent]
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

    mapper.createMapConfiguration(Object, InvoiceState, (input: any) => {
      return new InvoiceState(
        new UniqueIdentifier(input.linearId.externalId, input.linearId.id),
        CordaX500Name.parse(input.owner),
        CordaX500Name.parse(input.buyer),
        CordaX500Name.parse(input.supplier),
        CordaX500Name.parse(input.conductor),
        input.invoiceVersion,
        input.invoiceVersionDate,
        input.tixInvoiceVersion,
        input.invoiceNumber,
        input.invoiceType,
        input.reference,
        new Date(input.dueDate * 1000),
        input.offerId,
        input.amount,
        input.totalOutstanding,
        new Date(input.created * 1000),
        new Date(input.updated * 1000),
        new Date(input.expectedSettlementDate * 1000),
        new Date(input.settlementDate * 1000),
        new Date(input.mandatoryReconciliationDate * 1000),
        new Date(input.invoiceDate * 1000),
        input.status,
        input.rejectionReason,
        input.eligibleValue,
        input.invoicePurchaseValue,
        new Date(input.tradeDate * 1000),
        new Date(input.tradePaymentDate * 1000),
        input.invoicePayments,
        input.invoiceDilutions,
        input.cancelled,
        new Date(input.closeDate * 1000),
        input.originationNetwork,
        input.hash,
        input.currency,
        input.siteId,
        input.purchaseOrderNumber,
        input.purchaseOrderId,
        input.composerProgramId);
    });
  }
}
