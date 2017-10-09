import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { PurchaseOrderRequestMessage, PurchaseOrderResponseMessage } from '../domain/messages/purchase-order-messages';
import { StateAndRef } from '../domain/corda/state-and-ref';

@Injectable()
export class PurchaseOrderService extends BaseService {
  public getPurchaseOrders(): Observable<StateAndRef<any>[]> {
    return this.http
      .get(this.getUrl('purchaseorders'))
      .map(response => response.json() as StateAndRef<any>[]);
  }

  public createPurchaseOrder(message: PurchaseOrderRequestMessage): Observable<PurchaseOrderResponseMessage> {
    return this.http
      .post(this.getUrl('purchaseorders/create'), {
        supplier: message.supplier.toString(),
        value: message.value
      })
      .map(response => new PurchaseOrderResponseMessage(response.json().linearId, response.json().transactionId));
  }
}
