import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";

import { LoginService } from "app/main/content/_service/login.service";
import { FlatPropertyDefinition } from "app/main/content/_model/meta/property/property";
import { isNullOrUndefined } from "util";
import { ActivatedRoute } from "@angular/router";
import { User } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "app-property-build-form",
  templateUrl: "./property-build-form.component.html",
  styleUrls: ["./property-build-form.component.scss"]
})
export class PropertyBuildFormComponent implements OnInit {
  marketplaceId: string;
  marketplace: Marketplace;

  entryId: string;

  tenantAdmin: User;
  loaded: boolean;

  displayBuilder: boolean;
  builderType: string;

  tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    this.displayBuilder = true;

    await Promise.all([
      this.route.queryParams.subscribe(params => {
        if (isNullOrUndefined(params["type"] || params["type"] === "flat")) {
          this.builderType = "flat";
        } else {
          this.builderType = params["type"];
        }
      }),
      this.route.params.subscribe(params => {
        this.marketplaceId = params["marketplaceId"];
        this.entryId = params["entryId"];
      })
    ]);

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenantAdmin = globalInfo.user;
    this.marketplace = globalInfo.marketplace;
    this.tenant = globalInfo.tenants[0];
    this.loaded = true;
  }

  handleResultEvent(result: FlatPropertyDefinition<any>) {
    this.displayBuilder = false;
    window.history.back();
  }

  handleManagementEvent(event: string, dom: HTMLElement) {
    if (event === "disableScroll") {
      dom.style.overflow = "hidden";
    } else if (event === "enableScroll") {
      dom.style.overflow = "scroll";
    }
  }
}
