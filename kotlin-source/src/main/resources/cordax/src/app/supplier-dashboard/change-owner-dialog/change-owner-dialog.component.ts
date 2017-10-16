import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Currency } from '../../../api/domain/currency';
import { ChangeOwnerViewModel } from '../../../api/domain/models/change-owner';
import { NodeService } from '../../../api/domain/services/node.service';
import { TradeAssetService } from '../../../api/domain/services/trade-asset.service';
import { X500Name } from '../../../api/domain/x-500-name';

@Component({
  selector: 'app-change-owner-dialog',
  templateUrl: './change-owner-dialog.component.html',
  styleUrls: ['./change-owner-dialog.component.scss']
})
export class ChangeOwnerDialogComponent implements OnInit {
  private readonly currencies: Currency[] = Currency.getKnownCurrencies();
  private model: ChangeOwnerViewModel = new ChangeOwnerViewModel();
  private view = 0;
  public nodes: X500Name[] = [];

  constructor(
    private readonly nodeService: NodeService,
    private readonly tradeAssetService: TradeAssetService,
    private readonly dialogRef: MatDialogRef<ChangeOwnerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.nodeService
      .getNodes()
      .subscribe(nodes => this.nodes = nodes);

    this.model = new ChangeOwnerViewModel(data.linearId);
  }

  ngOnInit() {
  }

  okay(): void {
    this.view = 1;
    this.tradeAssetService
      .changeOwner(this.model)
      .subscribe(response => {
        this.cancel();
      });
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
