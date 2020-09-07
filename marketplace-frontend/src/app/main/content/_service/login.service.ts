import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UserRole } from "../_model/user";
import { GlobalInfo } from "../_model/global-info";
import { Observable, generate } from "rxjs";
import { global } from "@angular/compiler/src/util";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(private http: HttpClient, private httpClient: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post(
      "/core/login",
      { username: username, password: password },
      { observe: "response" }
    );
  }

  getActivationStatus(username: string) {
    return this.http.put("/core/login/activation-status", username);
  }

  getLoggedIn() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo.user);
        subscriber.complete();
      });
    } else {
      return this.http.get("/core/login");
    }
  }

  getLoggedInUserRole() {
    let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
    if (globalInfo) {
      return new Observable((subscriber) => {
        subscriber.next(globalInfo.userRole);
        subscriber.complete();
      });
    } else {
      return new Observable((subscriber) => {
        subscriber.next(UserRole.NONE);
        subscriber.complete();
      });
    }
  }

  refreshAccessToken(refreshToken: string) {
    return this.http.get("/core/login/refreshToken", {
      headers: this.createTokenHeader(refreshToken),
    });
  }

  private createTokenHeader(refreshToken: string): HttpHeaders {
    let reqOptions = new HttpHeaders().set("Content-Type", "application/json");

    if (refreshToken) {
      reqOptions = new HttpHeaders()
        .set("Content-Type", "application/json")
        .set("Refresh", refreshToken);
    }

    return reqOptions;
  }

  getGlobalInfo() {
    const observable = new Observable((subscriber) => {
      let globalInfo = JSON.parse(localStorage.getItem("globalInfo"));
      if (globalInfo) {
        subscriber.next(globalInfo);
        subscriber.complete();
      } else {
        this.httpClient.get(`/core/login/globalInfo`);
      }
    });

    return observable;
  }

  async generateGlobalInfo(role: UserRole, tenantIds: string[]) {
    let globalInfo = <GlobalInfo>(
      await this.httpClient
        .put(`/core/login/globalInfo/role/${role}`, tenantIds)
        .toPromise()
    );

    localStorage.setItem("globalInfo", JSON.stringify(globalInfo));
  }
}
