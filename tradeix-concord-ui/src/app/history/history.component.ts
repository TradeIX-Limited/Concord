import { Component, OnInit } from "@angular/core";
import { CordaX500Name, StateAndRef } from "api/domain/shared/corda";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";

@Component({
  selector: "app-history",
  templateUrl: "./history.component.html",
  styleUrls: ["./history.component.scss"]
})
export class HistoryComponent {
  private items: PurchaseOrderState[] = [];


  public setItems(items: PurchaseOrderState[]): void {
    this.items = items;
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }
}
