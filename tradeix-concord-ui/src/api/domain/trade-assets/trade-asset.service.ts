import { Injectable } from "@angular/core";
import { BaseService } from "../base.service";
import { Observable } from "rxjs/Observable";
import { CordaX500Name, StateAndRef } from "../shared/corda";
import "rxjs/add/operator/map";
import { TradeAssetState } from "api/domain/states/trade-asset-state";

@Injectable()
export class TradeAssetService extends BaseService {

  public getTradeAsset(externalId: string): Observable<StateAndRef<TradeAssetState>[]> {
    return this.http
      .get(this.createUrl(`tradeassets?externalId=${externalId}`))
      .map(response => response.json());
  }

  public getTradeAssetCount(): Observable<number> {
    return this.http
      .get(this.createUrl("tradeassets/count"))
      .map(response => response.json().count as number);
  }

  public getLatestTradeAssetHash(): Observable<string> {
    return this.http
      .get(this.createUrl("tradeassets/hash"))
      .map(response => response.json().hash as string);
  }

  public getAllTradeAssets(pageNumber: number, pageSize: number): Observable<StateAndRef<TradeAssetState>[]> {
    return this.http
      .get(this.createUrl(`tradeassets/all?page=${pageNumber}&count=${pageSize}`))
      .map(response => response.json());
  }
}
