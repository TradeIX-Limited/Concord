import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { Observable } from "rxjs/Observable";
import "rxjs/add/operator/catch";

@Injectable()
export class Config {
    private config: Object;
    private env: Object;

    constructor(private readonly http: Http) {
    }

    public load(): Promise<Config> {
        return new Promise((resolve, reject) => {
            this.http.get("app/config/env.json")
                .map(res => res.json())
                .subscribe((env_data) => {
                    this.env = env_data;
                    this.http.get("app/config/" + env_data.env + ".json")
                        .map(res => res.json())
                        .catch((error: any) => {
                            console.error(error);
                            return Observable.throw(error.json().error || "Server error");
                        })
                        .subscribe((data) => {
                            this.config = data;
                            resolve(data);
                        });
                });
        });
    }
}