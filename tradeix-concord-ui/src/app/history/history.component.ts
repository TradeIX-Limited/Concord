import { Component, OnInit } from "@angular/core";
import { CordaX500Name, StateAndRef } from "api/domain/shared/corda";
import { TradeAssetState } from "api/domain/states/trade-asset-state";

@Component({
  selector: "app-history",
  templateUrl: "./history.component.html",
  styleUrls: ["./history.component.scss"]
})
export class HistoryComponent {
  private items: StateAndRef<TradeAssetState>[] = [];


  public setItems(items: StateAndRef<TradeAssetState>[]): void {
    this.items = items;
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }
}
