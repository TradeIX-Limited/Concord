import { Injectable } from "@angular/core";
import { BaseService } from "../base.service";
import { Observable } from "rxjs/Observable";
import { CordaX500Name, StateAndRef } from "../shared/corda";
import "rxjs/add/operator/map";
import { TradeAssetState } from "api/domain/states/trade-asset-state";

@Injectable()
export class TradeAssetService extends BaseService {
  public getAllTradeAssets(): Observable<StateAndRef<TradeAssetState>[]> {
    return this.http
      .get(this.createUrl("tradeassets/all"))
      .map(response => response.json());
  }
}
