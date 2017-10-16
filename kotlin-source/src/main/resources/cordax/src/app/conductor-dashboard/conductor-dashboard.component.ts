import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NewPurchaseOrderDialogComponent } from './new-purchase-order-dialog/new-purchase-order-dialog.component';
import { TradeAssetService } from '../../api/domain/services/trade-asset.service';
import { TradeAssetState } from '../../api/domain/models/trade-asset';
import { StateAndRef } from '../../api/domain/corda';
import { X500Name } from '../../api/domain/x-500-name';

@Component({
  selector: 'app-conductor-dashboard',
  templateUrl: './conductor-dashboard.component.html',
  styleUrls: ['./conductor-dashboard.component.scss']
})
export class ConductorDashboardComponent implements OnInit {
  private states: StateAndRef<TradeAssetState>[] = [];

  constructor(
    private readonly dialog: MatDialog,
    private readonly tradeAssetService: TradeAssetService) {
  }

  getStates(): void {
    this.tradeAssetService
      .getTradeAssets()
      .subscribe(response => {
        if (response.length !== this.states.length) {
          this.states = response;
        }
      });
  }

  ngOnInit() {
    this.getStates();
    setInterval(() => this.getStates(), 2000);
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
