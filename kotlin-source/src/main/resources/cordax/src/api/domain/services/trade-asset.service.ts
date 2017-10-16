import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable } from 'rxjs/Observable';
import { TradeAssetState, IssueTradeAssetViewModel } from '../models/trade-asset';
import { ChangeOwnerViewModel } from '../models/change-owner';
import { TransactionResponseMessage, LinearTransactionResponseMessage } from '../messages';
import { StateAndRef } from '../corda';
import 'rxjs/add/operator/map';

@Injectable()
export class TradeAssetService extends BaseService {
  public getTradeAssets(): Observable<StateAndRef<TradeAssetState>[]> {
    return this.http
      .get(this.getUrl('tradeassets'))
      .map(response => response.json() as StateAndRef<TradeAssetState>[]);
  }

  public issueTradeAsset(tradeAssetViewModel: IssueTradeAssetViewModel): Observable<LinearTransactionResponseMessage> {
    return this.http
      .post(this.getUrl('tradeassets/issue'), tradeAssetViewModel.toRequestObject())
      .map(response => new LinearTransactionResponseMessage(response.json().linearId, response.json().transactionId));
  }

  public changeOwner(changeOwnerViewModel: ChangeOwnerViewModel): Observable<TransactionResponseMessage> {
    return this.http
      .put(this.getUrl('tradeassets/changeowner'), changeOwnerViewModel.toRequestObject())
      .map(response => new TransactionResponseMessage(response.json().transactionId));
  }
}
