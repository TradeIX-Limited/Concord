import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { X500Name } from '../domain/identity/x500-name';

@Injectable()
export class NodeService extends BaseService {
  public getLocalNode(): Observable<X500Name> {
    return this.http
      .get(this.getUrl('nodes/local'))
      .map(response => new X500Name(response.json()));
  }

  public getNodes(): Observable<X500Name[]> {
    return this.http
      .get(this.getUrl('nodes'))
      .map(response => (response.json() as string[]).map(name => new X500Name(name)));
  }
}
