import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NewPurchaseOrderDialogComponent } from './new-purchase-order-dialog/new-purchase-order-dialog.component';
import { TradeAssetService } from '../../api/domain/services/trade-asset.service';
import { TradeAssetState } from '../../api/domain/models/trade-asset';
import { StateAndRef } from '../../api/domain/corda';
import { X500Name } from '../../api/domain/x-500-name';

@Component({
  selector: 'app-buyer-dashboard',
  templateUrl: './buyer-dashboard.component.html',
  styleUrls: ['./buyer-dashboard.component.scss']
})
export class BuyerDashboardComponent implements OnInit {
  private states: StateAndRef<TradeAssetState>[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly tradeAssetService: TradeAssetService) {
  }

  ngOnInit() {
    setInterval(() => this.tradeAssetService
      .getTradeAssets()
      .subscribe(response => this.states = response), 2000);
  }

  openDialog() {
    this.dialog.open(NewPurchaseOrderDialogComponent, {
      width: '640px',
      disableClose: true
    });
  }

  getName(value: string): string {
    return !!value ? X500Name.parse(value).organizationName : '';
  }
}
