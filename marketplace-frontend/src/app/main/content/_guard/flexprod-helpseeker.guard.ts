import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";
import { LoginService } from "../_service/login.service";
import { ParticipantRole } from "../_model/user";

@Injectable({
  providedIn: "root",
})
export class FlexProdOrHelpseekerGuard implements CanActivate {
  constructor(private loginService: LoginService) {}

  canActivate(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.loginService
        .getLoggedInParticipantRole()
        .toPromise()
        .then((role: ParticipantRole) =>
          resolve(role == "FLEXPROD" || role == "HELP_SEEKER")
        );
    });
  }
}
