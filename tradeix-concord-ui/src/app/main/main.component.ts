import { Component, OnInit } from "@angular/core";
import { StateAndRef, CordaX500Name } from "api/domain/shared/corda";
import { TradeAssetState } from "api/domain/states/trade-asset-state";
import { NodeService } from "api/domain/nodes/node.service";
import { TradeAssetService } from "api/domain/trade-assets/trade-asset.service";

@Component({
  selector: "app-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"]
})
export class MainComponent implements OnInit {
  private states: TradeAssetState[] = [];
  private name: CordaX500Name = null;

  constructor(
    private readonly nodeService: NodeService,
    private readonly tradeAssetService: TradeAssetService) {
    this.update();
  }

  public ngOnInit(): void {
    (window as any)
      .setInterval(() => this.update(), 2000);
  }

  public update(): void {
    try {
      this.nodeService
        .getLocalNode()
        .subscribe(response => this.name = response);

      this.tradeAssetService
        .getAllTradeAssets()
        .subscribe(response => this.states = response.map(o => o.state.data));
    } catch (e) {
      console.log(e);
    }
  }

  public getName(value: string): CordaX500Name {
    return CordaX500Name.parse(value);
  }
}
