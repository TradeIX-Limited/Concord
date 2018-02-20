import { Injectable } from "@angular/core";
import { BaseService } from "api/domain/base.service";
import { Observable } from "rxjs/Observable";
import { Mapper } from "api/domain/shared/mapper";
import { InvoiceState } from "api/domain/invoices/invoice-state";

@Injectable()
export class InvoicesService extends BaseService {

  public getInvoiceState(externalId: string): Observable<InvoiceState[]> {
    return this.http
      .get(this.createUrl(`invoices?externalId=${externalId}`))
      .map(response => (response.json() as any[])
        .map(stateAndRef => Mapper.getInstance()
          .map(Object, InvoiceState, stateAndRef.state.data)));
  }

  public getInvoiceCount(): Observable<number> {
    return this.http
      .get(this.createUrl("invoices/count"))
      .map(response => Number(response.json().count));
  }

  public getMostRecentInvoiceHash(): Observable<string> {
    return this.http
      .get(this.createUrl("invoices/hash"))
      .map(response => response.json().hash);
  }

  public getAllInvoices(page: number = 1, count: number = 50): Observable<InvoiceState[]> {
    return this.http
      .get(this.createUrl(`invoices/all?page=${page}&count=${count}`))
      .map(response => (response.json() as any[])
        .map(stateAndRef => Mapper.getInstance()
          .map(Object, InvoiceState, stateAndRef.state.data)));
  }
}
