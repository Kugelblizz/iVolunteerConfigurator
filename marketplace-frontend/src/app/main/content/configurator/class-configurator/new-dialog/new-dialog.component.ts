import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/configurations';
import { ClassBrowseSubDialogData } from '../../class-configurator/browse-sub-dialog/browse-sub-dialog.component';

export interface NewClassConfigurationDialogData {
  label: string;
  marketplace: Marketplace;
}

@Component({
  selector: 'new-class-configuration-dialog',
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss']
})
export class NewClassConfigurationDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<NewClassConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private loginService: LoginService,
  ) {
  }

  allClassConfigurations: ClassConfiguration[];
  loaded = false;


  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.classConfigurationService.getAllClassConfigurationsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {

        this.allClassConfigurations = classConfigurations;


        //----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        //----

        this.loaded = true;
      });
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {

  }








}


