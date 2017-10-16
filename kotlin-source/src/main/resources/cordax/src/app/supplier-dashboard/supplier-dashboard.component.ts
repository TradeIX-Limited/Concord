import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ChangeOwnerDialogComponent } from './change-owner-dialog/change-owner-dialog.component';
import { TradeAssetService } from '../../api/domain/services/trade-asset.service';
import { TradeAssetState } from '../../api/domain/models/trade-asset';
import { StateAndRef } from '../../api/domain/corda';
import { X500Name } from '../../api/domain/x-500-name';

@Component({
  selector: 'app-supplier-dashboard',
  templateUrl: './supplier-dashboard.component.html',
  styleUrls: ['./supplier-dashboard.component.scss']
})
export class SupplierDashboardComponent implements OnInit {
  private states: StateAndRef<TradeAssetState>[] = [];
  private invalidate = false;

  constructor(
    private readonly dialog: MatDialog,
    private readonly tradeAssetService: TradeAssetService) {
  }

  getStates(): void {
    this.tradeAssetService
      .getTradeAssets()
      .subscribe(response => {
        if (this.invalidate || response.length !== this.states.length) {
          this.states = response;
          this.invalidate = false;
        }
      });
  }

  ngOnInit() {
    this.getStates();
    setInterval(() => this.getStates(), 2000);
  }

  openDialog(linearId: string) {
    this.dialog.open(ChangeOwnerDialogComponent, {
      width: '640px',
      disableClose: true,
      data: { linearId: linearId }
    }).afterClosed().subscribe(() => {
      this.invalidate = true;
    });
  }

  getName(value: string): string {
    return !!value ? X500Name.parse(value).organizationName : '';
  }
}
