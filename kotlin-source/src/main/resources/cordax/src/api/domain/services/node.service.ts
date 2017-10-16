import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable } from 'rxjs/Observable';
import { X500Name } from '../x-500-name';
import 'rxjs/add/operator/map'

@Injectable()
export class NodeService extends BaseService {
  public getLocalNode(): Observable<X500Name> {
    return this.http
      .get(this.getUrl('nodes/local'))
      .map(response => X500Name.parse(response.json()));
  }

  public getNodes(): Observable<X500Name[]> {
    return this.http
      .get(this.getUrl('nodes'))
      .map(response => (response.json() as string[]).map(name => X500Name.parse(name)));
  }
}
