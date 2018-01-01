import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Config } from "../../app/config/config";

@Injectable()
export class BaseService {
  private readonly config: Config = null;
  private useOrigin: boolean = true;
  private apiUrl: string = null;

  public constructor(protected readonly http: Http) {
    this.config = new Config(http);
    this.config.load()
      .then((o: any) => {
        this.useOrigin = o.useOrigin;
        this.apiUrl = o.apiUrl;
      });
  }

  protected createUrl(endpoint: string): string {
    return this.useOrigin
      ? `${window.location.origin}/api/${endpoint}`
      : `${this.apiUrl}/${endpoint}`;
  }
}
