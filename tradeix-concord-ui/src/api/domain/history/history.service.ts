import { Injectable, ViewChild } from "@angular/core";
import { MatDrawerContainer, MatDrawer } from "@angular/material";
import { IHistoryComponent } from "api/domain/history/history-component";
import { PurchaseOrderService } from "api/domain/purchase-orders/purchase-order.service";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";
import { InvoiceState } from "api/domain/invoices/invoice-state";
import { InvoicesService } from "api/domain/invoices/invoices.service";

@Injectable()
export class HistoryService {
  private drawerContainer: MatDrawerContainer = null;
  private purchaseOrderHistoryComponent: IHistoryComponent<PurchaseOrderState> = null;
  private invoiceHistoryComponent: IHistoryComponent<InvoiceState> = null;

  constructor(
    private readonly purchaseOrderService: PurchaseOrderService,
    private readonly invoiceService: InvoicesService) {
  }

  public setDrawerContainer(drawerContainer: MatDrawerContainer): void {
    this.drawerContainer = drawerContainer;
  }

  public setInvoiceHistoryComponent(invoiceHistoryComponent: IHistoryComponent<InvoiceState>): void {
    this.invoiceHistoryComponent = invoiceHistoryComponent;
  }

  public setPurchaseOrderHistoryComponent(purchaseOrderHistoryComponent: IHistoryComponent<PurchaseOrderState>): void {
    this.purchaseOrderHistoryComponent = purchaseOrderHistoryComponent;
  }

  public openDrawer(externalId: string, mode: "PURCHASE_ORDER" | "INVOICE"): void {
    this.reset();
    this.drawerContainer.open();

    switch (mode) {
      case "PURCHASE_ORDER":
        this.purchaseOrderService
          .getPurchaseOrderState(externalId)
          .subscribe(response => {
            this.purchaseOrderHistoryComponent.setItems(response);
          });
        break;
      case "INVOICE":
        this.invoiceService
          .getInvoiceState(externalId)
          .subscribe(response => {
            this.invoiceHistoryComponent.setItems(response);
          });
        break;
    }
  }

  private reset(): void {
    this.invoiceHistoryComponent.setItems([]);
    this.purchaseOrderHistoryComponent.setItems([]);
  }
}
