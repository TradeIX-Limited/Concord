import { Component, OnInit } from "@angular/core";
import { CordaX500Name, StateAndRef } from "api/domain/shared/corda";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";
import { IHistoryComponent } from "api/domain/history/history-component";

@Component({
  selector: "app-purchase-order-history",
  templateUrl: "./purchase-order-history.component.html",
  styleUrls: ["./purchase-order-history.component.scss"]
})
export class PurchaseOrderHistoryComponent implements IHistoryComponent<PurchaseOrderState> {
  private items: PurchaseOrderState[] = [];

  public setItems(items: PurchaseOrderState[]): void {
    this.items = items;
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }
}
