import { Component, OnInit } from '@angular/core';
import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
import { isNullOrUndefined } from 'util';
import { ActivatedRoute } from '@angular/router';
import { User } from 'app/main/content/_model/user';
import { Tenant } from 'app/main/content/_model/tenant';

@Component({
  selector: "app-property-build-form",
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {
  entryId: string;
  tenantAdmin: User;
  loaded: boolean;
  displayBuilder: boolean;
  builderType: string;
  tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
  ) { }

  async ngOnInit() {
    this.displayBuilder = true;

    await Promise.all([
      this.route.queryParams.subscribe(params => {
        if (isNullOrUndefined(params['type'] || params['type'] === 'flat')) {
          this.builderType = 'flat';
        } else {
          this.builderType = params['type'];
        }
      }),
      this.route.params.subscribe(params => {
        this.entryId = params['entryId'];
      })
    ]);

    this.loaded = true;
  }

  handleResultEvent(result: FlatPropertyDefinition<any>) {
    this.displayBuilder = false;
    window.history.back();
  }

  handleManagementEvent(event: string, dom: HTMLElement) {
    if (event === 'disableScroll') {
      dom.style.overflow = 'hidden';
    } else if (event === 'enableScroll') {
      dom.style.overflow = 'scroll';
    }
  }
}
