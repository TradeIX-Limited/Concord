import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";

@Component({
  selector: "app-delivery-information",
  templateUrl: "./delivery-information.component.html",
  styleUrls: ["./delivery-information.component.scss"]
})
export class DeliveryInformationComponent {
  public constructor( @Inject(MAT_DIALOG_DATA) public readonly data: PurchaseOrderState) {
  }
}
