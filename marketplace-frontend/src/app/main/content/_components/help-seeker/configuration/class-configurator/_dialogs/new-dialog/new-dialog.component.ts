import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { FormControl, FormGroup } from '@angular/forms';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { stringUniqueValidator } from 'app/main/content/_validator/string-unique.validator';
import { isNullOrUndefined } from 'util';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { Tenant } from 'app/main/content/_model/tenant';

export interface NewClassConfigurationDialogData {
  classConfiguration: ClassConfiguration;
  relationships: Relationship[];
  classDefinitions: ClassDefinition[];
}

@Component({
  selector: "new-class-configuration-dialog",
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss']
})
export class NewClassConfigurationDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<NewClassConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private relationshipsService: RelationshipService,
    private classDefintionService: ClassDefinitionService,
    private loginService: LoginService
  ) { }
  tenant: Tenant;

  dialogForm: FormGroup;
  allClassConfigurations: ClassConfiguration[];
  showEditDialog: boolean;
  loaded = false;

  globalInfo: GlobalInfo;

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = this.globalInfo.tenants[0];

    this.classConfigurationService
      .getClassConfigurationsByTenantId(this.globalInfo.marketplace, this.tenant.id)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.dialogForm = new FormGroup({
          label: new FormControl('', isNullOrUndefined(this.data.classConfiguration) ?
            stringUniqueValidator(this.allClassConfigurations.map(c => c.name))
            : stringUniqueValidator(this.allClassConfigurations.map(c => c.name), [this.data.classConfiguration.name])
          ),
          description: new FormControl('')
          // rootLabel: new FormControl('')
        });

        if (!isNullOrUndefined(this.data.classConfiguration)) {
          this.showEditDialog = true;
          this.dialogForm
            .get('label')
            .setValue(this.data.classConfiguration.name);
          this.dialogForm
            .get('description')
            .setValue(this.data.classConfiguration.description);
        }
        // ----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // ----

        this.loaded = true;
      });
  }

  displayErrorMessage(key: string) {
    if (key === 'label') {
      return 'Name bereits vorhanden';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onCreateClick() {
    if (this.checkFormInvalid()) {
      return;
    }

    const formValues = this.getFormValues();

    this.classConfigurationService
      .createNewClassConfiguration(this.globalInfo.marketplace, this.tenant.id, formValues.name, formValues.description)
      .toPromise()
      .then((ret: ClassConfiguration) => {
        this.data.classConfiguration = ret;
      }).then(() => {
        Promise.all([
          this.relationshipsService
            .getRelationshipsById(this.globalInfo.marketplace, this.data.classConfiguration.relationshipIds)
            .toPromise()
            .then((ret: Relationship[]) => {
              this.data.relationships = ret;
            }),
          this.classDefintionService
            .getClassDefinitionsById(
              this.globalInfo.marketplace,
              this.data.classConfiguration.classDefinitionIds,
              this.tenant.id
            )
            .toPromise()
            .then((ret: ClassDefinition[]) => {
              this.data.classDefinitions = ret;
            })
        ]).then(() => {
          this.dialogRef.close(this.data);
        });
      });
  }

  onSaveClick() {
    if (this.checkFormInvalid()) {
      return;
    }
    const formValues = this.getFormValues();
    this.classConfigurationService
      .saveClassConfigurationMeta(this.globalInfo.marketplace, this.data.classConfiguration.id, formValues.name, formValues.description)
      .toPromise().then((ret: ClassConfiguration) => {
        this.data.classConfiguration = ret;
        this.dialogRef.close(this.data);
      });
  }

  private checkFormInvalid() {
    this.dialogForm.get('label').markAsTouched();
    this.dialogForm.get('description').markAsTouched();
    return this.dialogForm.invalid;
  }

  private getFormValues(): { name: string; description: string } {
    const name = this.dialogForm.get('label').value;
    const description = this.dialogForm.get('description').value;

    return { name, description };
  }
}
