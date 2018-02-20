import { Component, OnInit } from "@angular/core";
import { CordaX500Name, StateAndRef } from "api/domain/shared/corda";
import { InvoiceState } from "api/domain/invoices/invoice-state";
import { IHistoryComponent } from "api/domain/history/history-component";

@Component({
  selector: "app-invoice-history",
  templateUrl: "./invoice-history.component.html",
  styleUrls: ["./invoice-history.component.scss"]
})
export class InvoiceHistoryComponent implements IHistoryComponent<InvoiceState> {
  public items: InvoiceState[] = [];

  public setItems(items: InvoiceState[]): void {
    this.items = items;
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }
}
