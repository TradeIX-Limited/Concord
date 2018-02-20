import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material";
import { InvoiceState } from "api/domain/invoices/invoice-state";

@Component({
  selector: "app-invoice-information",
  templateUrl: "./invoice-information.component.html",
  styleUrls: ["./invoice-information.component.scss"]
})
export class InvoiceInformationComponent {
  public constructor( @Inject(MAT_DIALOG_DATA) public readonly data: InvoiceState) {
  }
}
