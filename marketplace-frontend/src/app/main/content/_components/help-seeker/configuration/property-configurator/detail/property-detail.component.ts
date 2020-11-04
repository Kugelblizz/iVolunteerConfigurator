import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserRole, User } from 'app/main/content/_model/user';
import { FlatPropertyDefinition, PropertyParentTemplate, PropertyParentSubTemplate } from 'app/main/content/_model/meta/property/property';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { Tenant } from 'app/main/content/_model/tenant';

@Component({
  selector: "app-property-detail",
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss'],
})
export class PropertyDetailComponent implements OnInit {
  role: UserRole;
  tenantAdmin: User;
  tenant: Tenant;
  propertyDefintion: FlatPropertyDefinition<any>;

  templateItem: PropertyParentTemplate;
  subtemplateItem: PropertyParentSubTemplate;

  isLoaded: boolean;
  columnsToDisplay = ['value'];

  constructor(
    private route: ActivatedRoute,
    private propertyDefinitionService: FlatPropertyDefinitionService
  ) {
    this.isLoaded = false;
  }

  async ngOnInit() {

    let parameters;
    let queryParameters;

    Promise.all([
      this.route.params.subscribe((params) => {
        parameters = params;
      }),
      this.route.queryParams.subscribe((params) => {
        queryParameters = params;
      }),
    ]).then(() => {
      this.loadProperty(
        parameters['marketplaceId'],
        parameters['templateId'],
        parameters['subtemplateId'],
        parameters['propertyId'],
        queryParameters['ref']
      );
    });
  }

  loadProperty(
    marketplaceId: string,
    templateId: string,
    subtemplateId: string,
    propId: string,
    ref: string
  ): void {


    if (ref === 'list') {
      this.propertyDefinitionService
        .getPropertyDefinitionById(null, propId, this.tenant.id)
        .toPromise()
        .then((propertyDefintion: FlatPropertyDefinition<any>) => {
          this.propertyDefintion = propertyDefintion;
        })
        .then(() => {
          this.isLoaded = true;
        });
    } else if (ref === 'template') {
    } else if (ref === 'subtemplate') {
      // this.userDefinedTaskTemplateService
      //   .getPropertyFromSubTemplate(
      //     this.marketplace,
      //     templateId,
      //     subtemplateId,
      //     propId
      //   )
      //   .toPromise()
      //   .then((propertyDefintion: FlatPropertyDefinition<any>) => {
      //     this.propertyDefintion = propertyDefintion;
      //   });
    }
  }

  navigateBack() {
    window.history.back();
  }
}
