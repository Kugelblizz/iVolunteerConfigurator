import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../../_service/core-helpseeker.service';
import { UserDefinedTaskTemplateService } from "../../../../_service/user-defined-task-template.service";

import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Marketplace } from '../../../../_model/marketplace';
import { UserDefinedTaskTemplate, UserDefinedTaskTemplateStub } from "../../../../_model/user-defined-task-template";
import { Participant } from '../../../../_model/participant';
import { isNullOrUndefined } from 'util';
import { fuseAnimations } from '@fuse/animations';
import { DialogFactoryComponent } from '../../../../_shared_components/dialogs/_dialog-factory/dialog-factory.component';


@Component({
  templateUrl: './user-defined-task-template-list.component.html',
  styleUrls: ['./user-defined-task-template-list.component.scss'],
  animations: fuseAnimations,
  providers: [DialogFactoryComponent]
})
export class UserDefinedTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<UserDefinedTaskTemplateStub>();
  displayedColumns = ['id', 'name', 'description', 'actions'];
  marketplace: Marketplace;
  isLoaded: boolean =  false;

  constructor(private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private dialogFactory: DialogFactoryComponent) 
    { }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.userDefinedTaskTemplateService.getAllTaskTemplates(this.marketplace, true).toPromise().then((templates: UserDefinedTaskTemplateStub[]) => {

            this.dataSource.data = templates;
            this.isLoaded = true;   
          });
        }
      });
    });
  }

  onRowSelect(t: UserDefinedTaskTemplateStub) {
    console.log(t.id + ": " + t.name + " row selected!");
    console.log("navigate to TaskTemplate detail page");
    if (t.kind == 'single') {
      this.router.navigate(['/main/task-templates/user/detail/single/' + this.marketplace.id + '/' + t.id]);
    } else if (t.kind = 'nested') {
      console.log("TODO navigate to nested form builder");
      console.log(t);
      this.router.navigate(['main/task-templates/user/detail/nested/' + this.marketplace.id + '/' + t.id]);
    }
    
  }

  newSingleTaskTemplate() {
    console.log("clicked new TaskTemplate!");
    console.log("navigate to new TaskTemplate from");

    this.dialogFactory.newTaskTemplateDialog().then((result: string[]) => {
      this.userDefinedTaskTemplateService.newRootSingleTaskTemplate(this.marketplace, result[0], result[1]).toPromise().then((t: UserDefinedTaskTemplate) => {
        if (!isNullOrUndefined(t)) {
          this.router.navigate(['/main/task-templates/user/detail/single/' + this.marketplace.id + '/' + t.id]);
        }
      });
    });
  }

  newNestedTaskTemplate() {
    console.log("clicked new nested Task Template");


    this.dialogFactory.newTaskTemplateDialog().then((result: string[]) => {
      this.userDefinedTaskTemplateService.newRootMultiTaskTemplate(this.marketplace, result[0], result[1]).toPromise().then((t: UserDefinedTaskTemplate) => {
        if (!isNullOrUndefined(t)) {
          this.router.navigate(['/main/task-templates/user/detail/nested/' + this.marketplace.id + '/' + t.id]);
        }
      });
    });

  }

  //create dialog
  newTaskTemplateFromExisting() {
    //Open dialog with selection of template (maybe with a preview of properties inside template), then create new
    let idOfCopy = this.dataSource.data[0].id;

    let entries: {id: string, label: string, description: string}[] = [];

    for (let template of this.dataSource.data) {
      entries.push({id: template.id, label: template.name, description: template.description});
    }


    this.dialogFactory.chooseTemplateToCopyDialog(entries).then((result: any) => {

      if (!isNullOrUndefined(result)) {
        console.log("Result: ");
        console.log(result);

        this.userDefinedTaskTemplateService.newTaskTemplateFromExisting(this.marketplace, result.copyId, result.newName, result.newDescription).toPromise().then((newTemplate: UserDefinedTaskTemplate) => {
          console.log("done - new Template:");
          console.log(newTemplate);


          this.dataSource.data.push({id: newTemplate.id, name: newTemplate.name, description: newTemplate.description, kind: newTemplate.kind});

          this.dataSource.data = this.dataSource.data;
          
        });

      } else {
        console.log("cancelled");
      }
    });

    
    // this.userDefinedTaskTemplateService.newTaskTemplateFromExisting(this.marketplace, idOfCopy, "COPY", "DESC").toPromise().then(() => {
    //   this.isLoaded = false;
    //   this.ngOnInit();
    // });
  }


  removeTemplate(taskTemplate: UserDefinedTaskTemplateStub, i: number) {
    this.dialogFactory.confirmationDialog("Remove Sub-Template " + taskTemplate.name + "...", 
    "Are you sure you want to delete the Sub-Template " + taskTemplate.name + "? \nThis action accont be reverted" )
      .then((cont: boolean) => {
        if (cont) {
          this.userDefinedTaskTemplateService.deleteRootTaskTemplate(this.marketplace, taskTemplate.id).toPromise().then((success: boolean) => {
            if (success) {
              this.dataSource.data.splice(i, 1);
              this.dataSource.data = this.dataSource.data;
            }
          });
        }
    });
  }
}