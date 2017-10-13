import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NewPurchaseOrderDialogComponent } from './new-purchase-order-dialog/new-purchase-order-dialog.component';

@Component({
  selector: 'app-buyer-dashboard',
  templateUrl: './buyer-dashboard.component.html',
  styleUrls: ['./buyer-dashboard.component.scss']
})
export class BuyerDashboardComponent implements OnInit {

  constructor(private readonly dialog: MatDialog) { }

  ngOnInit() {
  }

  openDialog() {
    this.dialog.open(NewPurchaseOrderDialogComponent, {
      width: '640px',
      disableClose: true
    });
  }
}
