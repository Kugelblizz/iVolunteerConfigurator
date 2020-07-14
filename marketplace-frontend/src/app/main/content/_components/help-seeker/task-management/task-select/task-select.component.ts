import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material";
import {
  ClassArchetype,
} from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { FormBuilder } from "@angular/forms";
import { Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { isNullOrUndefined } from "util";
import { ClassDefinitionDTO } from "app/main/content/_model/meta/class";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  templateUrl: "./task-select.component.html",
  styleUrls: ["./task-select.component.scss"],
})
export class FuseTaskSelectComponent implements OnInit {
  marketplace: Marketplace;
  dataSource = new MatTableDataSource<ClassDefinitionDTO>();
  displayedColumns = ["name", "configuration"];
  helpseeker: User;
  tenant: Tenant;

  constructor(
    formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService,
    private classDefinitionService: ClassDefinitionService,
  ) { }

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.helpseeker = globalInfo.user;
    this.tenant = globalInfo.tenants[0];
    this.marketplace = globalInfo.marketplace;

    if (!isNullOrUndefined(this.marketplace)) {
      let tasks = <ClassDefinitionDTO[]>(
        await this.classDefinitionService
          .getByArchetype(
            this.marketplace,
            ClassArchetype.TASK,
            this.helpseeker.subscribedTenants.find(
              (t) => t.role === UserRole.HELP_SEEKER
            ).tenantId
          )
          .toPromise()
      );

      this.dataSource.data = tasks
        .filter((t) => t.configurationId != null)
        .sort((c1, c2) => c1.configurationId.localeCompare(c2.configurationId));
    }
  }

  onRowSelect(row) {
    this.router.navigate(
      [`main/configurator/instance-editor/${this.marketplace.id}`],
      { queryParams: [row.id] }
    );
  }

  private isFF() {
    return this.tenant.name == "FF Eidenberg";
  }

  private isMV() {
    return this.tenant.name === "Musikverein_Schwertberg";
  }
  private isOther() {
    return !this.isFF() && !this.isMV();
  }
}
