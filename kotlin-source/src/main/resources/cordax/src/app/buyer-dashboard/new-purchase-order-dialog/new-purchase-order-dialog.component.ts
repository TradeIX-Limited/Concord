import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Currency } from '../../../api/domain/currency';

@Component({
  selector: 'app-new-purchase-order-dialog',
  templateUrl: './new-purchase-order-dialog.component.html',
  styleUrls: ['./new-purchase-order-dialog.component.scss']
})
export class NewPurchaseOrderDialogComponent implements OnInit {
  private readonly currencies: Currency[] = Currency.getKnownCurrencies();
  private view: ViewState = ViewState.Collect;

  public items: string[] = ['Alice', 'Bob', 'Charlie'];

  constructor(private readonly dialogRef: MatDialogRef<NewPurchaseOrderDialogComponent>) {
  }

  ngOnInit() {
  }

  okay(): void {
    this.view = ViewState.Conduct;
  }

  cancel(): void {
    this.dialogRef.close();
  }

  stringify(o: any) {
    return JSON.stringify(o);
  }
}

enum ViewState {
  'Collect',
  'Conduct',
  'Success',
  'Failure'
}
