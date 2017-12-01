import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { Observable } from 'rxjs/Observable';
import { CordaX500Name } from '../shared/corda';
import 'rxjs/add/operator/map'

@Injectable()
export class NodeService extends BaseService {
  public getLocalNode(): Observable<CordaX500Name> {
    return this.http
      .get(this.createUrl('nodes/local'))
      .map(response => CordaX500Name.parse(response.json().localNode));
  }

  public getAllNodes(): Observable<CordaX500Name[]> {
    return this.http
      .get(this.createUrl('nodes/all'))
      .map(response => (response.json().nodes as string[])
        .map(name => CordaX500Name.parse(name)));
  }

  public getPeerNodes(): Observable<CordaX500Name[]> {
    return this.http
      .get(this.createUrl('nodes/peers'))
      .map(response => (response.json().nodes as string[])
        .map(name => CordaX500Name.parse(name)));
  }
}
