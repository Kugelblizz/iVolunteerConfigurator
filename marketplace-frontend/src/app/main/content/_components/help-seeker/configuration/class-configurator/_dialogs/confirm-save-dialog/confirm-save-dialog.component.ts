import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';

export interface ConfirmClassConfigurationSaveDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];

  deletedClassDefintions: string[];
  deletedRelationships: string[];

}

@Component({
  selector: 'confirm-class-configuration-save-dialog',
  templateUrl: './confirm-save-dialog.component.html',
  styleUrls: ['./confirm-save-dialog.component.scss']
})
export class ConfirmClassConfigurationSaveDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ConfirmClassConfigurationSaveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmClassConfigurationSaveDialogData,
    private classConfigurationService: ClassConfigurationService,
    private relationshipService: RelationshipService,
    private classDefinitionService: ClassDefinitionService,
  ) {
  }

  loaded: boolean;

  async ngOnInit() {

  }

  onOKClick() {
    Promise.all([
      this.relationshipService
        .addAndUpdateRelationships(null, this.data.relationships)
        .toPromise().then((ret: Relationship[]) => {
          this.data.relationships = ret;
        }),

      this.classDefinitionService
        .addOrUpdateClassDefintions(null, this.data.classDefinitions)
        .toPromise().then((ret: ClassDefinition[]) => {
          this.data.classDefinitions = ret;
        }),

      this.classDefinitionService
        .deleteClassDefinitions(null, this.data.deletedClassDefintions)
        .toPromise().then((ret: any) => {
          this.data.deletedClassDefintions = [];
        }),

      this.relationshipService
        .deleteRelationships(null, this.data.deletedRelationships)
        .toPromise().then((ret: any) => {
          this.data.deletedRelationships = [];
        }),
    ]).then(() => {
      this.classConfigurationService
        .saveClassConfiguration(null, this.data.classConfiguration)
        .toPromise().then((ret: ClassConfiguration) => {
          this.data.classConfiguration = ret;
        }).then(() => {
          this.dialogRef.close(this.data);
        });
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


