import { Component, OnInit } from '@angular/core';
import { X500Name } from './api/domain/identity/x500-name';
import { PurchaseOrderRequestMessage, PurchaseOrderResponseMessage } from './api/domain/messages/purchase-order-messages';

import { NodeService } from './api/services/node.service';
import { PurchaseOrderService } from './api/services/purchase-order.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  nodeName: X500Name;
  suppliers: X500Name[];
  dataSource: any[] = [];

  public constructor(
    private readonly nodeService: NodeService,
    private readonly purchaseOrderService: PurchaseOrderService) {
  }

  ngOnInit() {
    this.nodeService
      .getLocalNode()
      .subscribe(response => {
        this.nodeName = response;
        window.document.title = `Cordax | ${this.nodeName.organizationName}`;
      });

    this.nodeService
      .getNodes()
      .subscribe(response => this.suppliers = response.filter(name => name.commonName.indexOf('Supplier') > -1));

    window.setInterval(() => {
      this.purchaseOrderService
        .getPurchaseOrders()
        .subscribe(response => this.dataSource = response);
    }, 2000);
  }

  create(supplier: string, value: number) {
    this.purchaseOrderService
      .createPurchaseOrder(new PurchaseOrderRequestMessage(new X500Name(supplier), value))
      .subscribe(response => alert(`Transaction ${response.transactionId} committed to blockchain.`));
  }

  getName(x500name: string): string {
    return new X500Name(x500name).organizationName;
  }
}
