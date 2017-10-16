import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class BaseService {
  public constructor(protected readonly http: Http) {
  }

  protected getUrl(endpoint: string): string {
    return `http://localhost:10007/api/${endpoint}`;
  }
}
