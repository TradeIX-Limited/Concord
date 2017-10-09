import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class BaseService {
  public constructor(protected readonly http: Http) {
  }

  protected getUrl(endpoint: string): string {
    return `${window.location.origin}/api/${endpoint}`;
  }
}
