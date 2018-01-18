import { Injectable } from "@angular/core";
import { BaseService } from "api/domain/base.service";
import { Observable } from "rxjs/Observable";
import { Mapper } from "api/domain/shared/mapper";
import { PurchaseOrderState } from "api/domain/purchase-orders/purchase-order-state";

@Injectable()
export class PurchaseOrderService extends BaseService {

  public getPurchaseOrderState(externalId: string): Observable<PurchaseOrderState[]> {
    return this.http
      .get(this.createUrl(`purchaseorders?externalId=${externalId}`))
      .map(response => (response.json() as any[])
        .map(stateAndRef => Mapper.getInstance()
          .map(Object, PurchaseOrderState, stateAndRef.state.data)));
  }

  public getPurchaseOrderCount(): Observable<number> {
    return this.http
      .get(this.createUrl("purchaseorders/count"))
      .map(response => Number(response.json().count));
  }

  public getMostRecentPurchaseOrderHash(): Observable<string> {
    return this.http
      .get(this.createUrl("purchaseorders/hash"))
      .map(response => response.json().hash);
  }

  public getAllPurchaseOrders(page: number = 1, count: number = 50): Observable<PurchaseOrderState[]> {
    return this.http
      .get(this.createUrl(`purchaseorders/all?page=${page}&count=${count}`))
      .map(response => (response.json() as any[])
        .map(stateAndRef => Mapper.getInstance()
          .map(Object, PurchaseOrderState, stateAndRef.state.data)));
  }
}
