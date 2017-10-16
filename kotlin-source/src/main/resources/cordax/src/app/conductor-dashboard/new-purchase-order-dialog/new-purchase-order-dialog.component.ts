import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Currency } from '../../../api/domain/currency';
import { IssueTradeAssetViewModel } from '../../../api/domain/models/trade-asset';
import { NodeService } from '../../../api/domain/services/node.service';
import { TradeAssetService } from '../../../api/domain/services/trade-asset.service';
import { X500Name } from '../../../api/domain/x-500-name';

@Component({
  selector: 'app-new-purchase-order-dialog',
  templateUrl: './new-purchase-order-dialog.component.html',
  styleUrls: ['./new-purchase-order-dialog.component.scss']
})
export class NewPurchaseOrderDialogComponent implements OnInit {
  private readonly currencies: Currency[] = Currency.getKnownCurrencies();
  private model: IssueTradeAssetViewModel = new IssueTradeAssetViewModel();
  private view = 0;
  public nodes: X500Name[] = [];

  constructor(
    private readonly nodeService: NodeService,
    private readonly tradeAssetService: TradeAssetService,
    private readonly dialogRef: MatDialogRef<NewPurchaseOrderDialogComponent>) {
    this.nodeService
      .getNodes()
      .subscribe(nodes => this.nodes = nodes);
  }

  ngOnInit() {
  }

  okay(): void {
    this.view = 1;
    this.tradeAssetService
      .issueTradeAsset(this.model)
      .subscribe(response => {
        this.cancel();
      });
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
