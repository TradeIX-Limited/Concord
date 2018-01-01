import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { StateAndRef, CordaX500Name } from "api/domain/shared/corda";
import { TradeAssetState } from "api/domain/states/trade-asset-state";
import { NodeService } from "api/domain/nodes/node.service";
import { TradeAssetService } from "api/domain/trade-assets/trade-asset.service";
import { MatPaginator } from "@angular/material";
import { HistoryService } from "api/domain/history/history.service";
import { MatDrawerContainer, MatDrawer } from "@angular/material";

@Component({
  selector: "app-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"]
})
export class MainComponent implements OnInit {
  @ViewChild("invoicePaginator") invoicePaginator: MatPaginator;

  private states: TradeAssetState[] = null;
  private name: CordaX500Name = null;

  private pageNumber: number = 1;
  private pageSize: number = 50;

  private pageInterval: number;

  private hash: string = undefined;
  private visibleState: string = "loading";

  constructor(
    private readonly tradeAssetService: TradeAssetService,
    private readonly historyService: HistoryService) {
    this.update();
  }

  public ngOnInit(): void {
    this.invoicePaginator.pageSize = this.pageSize;

    (window as any)
      .setInterval(() => this.update(), 5000);
  }

  public update(forceUpdate: boolean = false): void {
    try {
      this.tradeAssetService.getLatestTradeAssetHash().subscribe(hash => {
        if (hash !== this.hash) {
          this.hash = hash;
          forceUpdate = true;
        }

        if (forceUpdate) {
          this.tradeAssetService.getTradeAssetCount().subscribe(count => {
            this.invoicePaginator.length = count;

            if (count === 0) {
              this.visibleState = "empty";
            } else {
              this.visibleState = "full";
              this.tradeAssetService
                .getAllTradeAssets(this.pageNumber, this.pageSize)
                .subscribe(assets => this.states = assets.map(o => o.state.data));
            }
          });
        }
      });
    } catch (e) { }
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }

  public onPage(event: any): void {
    clearInterval(this.pageInterval);
    this.pageInterval = (window as any).setInterval(() => {
      this.pageNumber = event.pageIndex + 1;
      this.update(true);
    }, 1000);
  }

  public onOpenDrawer(externalId: string): void {
    this.historyService.openDrawer(externalId);
  }
}
