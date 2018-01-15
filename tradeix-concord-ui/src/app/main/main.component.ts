import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { StateAndRef, CordaX500Name } from "api/domain/shared/corda";
import {PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";
import { NodeService } from "api/domain/nodes/node.service";
import { PurchaseOrderService } from "api/domain/purchase-orders/purchase-order.service";
import { MatPaginator } from "@angular/material";
import { HistoryService } from "api/domain/history/history.service";

@Component({
  selector: "app-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"]
})
export class MainComponent implements OnInit {
  @ViewChild("purchaseOrderPaginator") purchaseOrderPaginator: MatPaginator;

  private states: PurchaseOrderState[] = null;
  private name: CordaX500Name = null;

  private hash: string = undefined;
  private visibleState: string = "loading";
  private pageNumber: number = 1;
  private pageSize: number = 50;
  private pageTimeout: number;

  constructor(
    private readonly purchaseOrderService: PurchaseOrderService,
    private readonly historyService: HistoryService) {
    this.update();
  }

  public ngOnInit(): void {
    this.purchaseOrderPaginator.pageSize = this.pageSize;
    window.setInterval(() => this.update(), 5000);
  }

  public update(forceUpdate: boolean = false): void {
    try {
      this.purchaseOrderService
        .getMostRecentPurchaseOrderHash()
        .subscribe(hash => {
          if (hash !== this.hash) {
            this.hash = hash;
            forceUpdate = true;
          }

          if (forceUpdate) {
            this.purchaseOrderService
              .getPurchaseOrderCount()
              .subscribe(count => {
                this.purchaseOrderPaginator.length = count;

                if (count === 0) {
                  this.visibleState = "empty";
                } else {
                  this.visibleState = "full";
                  this.purchaseOrderService
                    .getAllPurchaseOrders(this.pageNumber, this.pageSize)
                    .subscribe(assets => this.states = assets);
                }
              });
          }
        });
    } catch (e) {
      console.error(e);
    }
  }

  public onPage(event: any): void {
    window.clearTimeout(this.pageTimeout);
    this.pageTimeout = window.setTimeout(() => {
      this.visibleState = "loading";
      this.pageNumber = event.pageIndex + 1;
      this.update(true);
    }, 1000);
  }

  public onOpenDrawer(externalId: string): void {
    this.historyService.openDrawer(externalId);
  }
}
