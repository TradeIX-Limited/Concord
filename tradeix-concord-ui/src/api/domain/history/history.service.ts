import { Injectable, ViewChild } from "@angular/core";
import { MatDrawerContainer, MatDrawer } from "@angular/material";
import { HistoryComponent } from "app/history/history.component";
import { PurchaseOrderService } from "api/domain/purchase-orders/purchase-order.service";

@Injectable()
export class HistoryService {
  private drawerContainer: MatDrawerContainer = null;
  private historyComponent: HistoryComponent = null;

  constructor(private readonly tradeAssetService: PurchaseOrderService) {
  }

  public setComponents(drawerContainer: MatDrawerContainer, historyComponent: HistoryComponent): void {
    this.drawerContainer = drawerContainer;
    this.historyComponent = historyComponent;
  }

  public openDrawer(externalId: string): void {
    this.historyComponent.setItems([]);
    this.drawerContainer.open();
    this.tradeAssetService
      .getPurchaseOrderState(externalId)
      .subscribe(response => {
        this.historyComponent.setItems(response);
      });
  }
}
