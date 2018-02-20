import { Component, OnInit, ViewChild, ComponentRef } from "@angular/core";
import { NodeService } from "api/domain/nodes/node.service";
import { CordaX500Name } from "api/domain/shared/corda";
import { HistoryService } from "api/domain/history/history.service";
import { MatDrawerContainer, MatDrawer } from "@angular/material";
import { PurchaseOrderHistoryComponent } from "app/purchase-order-history/purchase-order-history.component";
import { InvoiceHistoryComponent } from "app/invoice-history/invoice-history.component";

@Component({
  selector: "app-root",
  templateUrl: "./root.component.html",
  styleUrls: ["./root.component.scss"]
})
export class RootComponent implements OnInit {
  @ViewChild("drawerContainer") drawerContainer: MatDrawerContainer;
  @ViewChild("purchaseOrderHistory") purchaseOrderHistory: PurchaseOrderHistoryComponent;
  @ViewChild("invoiceHistory") invoiceHistory: InvoiceHistoryComponent;

  public nodeName: CordaX500Name = null;

  constructor(
    private readonly nodeService: NodeService,
    private readonly historyService: HistoryService) {
  }

  public ngOnInit(): void {
    this.historyService.setDrawerContainer(this.drawerContainer);
    this.historyService.setPurchaseOrderHistoryComponent(this.purchaseOrderHistory);
    this.historyService.setInvoiceHistoryComponent(this.invoiceHistory);

    (window as any).setTimeout(() => this.nodeService
      .getLocalNode()
      .subscribe(response => this.nodeName = response), 2000);
  }
}
