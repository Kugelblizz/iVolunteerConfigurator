import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { PropertyInstance, PropertyItem, PropertyDefinition } from '../_model/meta/Property';
import { Marketplace } from '../_model/marketplace';
import { UserDefinedTaskTemplate } from '../_model/user-defined-task-template';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { QuestionService } from '../_service/question.service';
import { QuestionBase } from '../_model/dynamic-forms/questions';
import { isNullOrUndefined, isNull } from 'util';
import { DialogFactoryComponent } from '../_components/dialogs/_dialog-factory/dialog-factory.component';
import { SortDialogData } from '../_components/dialogs/sort-dialog/sort-dialog.component';
import { PropertyInstanceService } from '../_service/meta/core/property/property-instance.service';
import { PropertyDefinitionService } from '../_service/meta/core/property/property-definition.service';

@Component({
  selector: 'user-defined-task-template-detail-single',
  templateUrl: './user-defined-task-template-detail-single.html',
  styleUrls: ['./user-defined-task-template-detail-single.scss'],
  providers:  [QuestionService, DialogFactoryComponent]
})
export class SingleUserDefinedTaskTemplateDetailComponent implements OnInit {

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  template: UserDefinedTaskTemplate;
  isLoaded: boolean;
  dialogIds: string[];
  questions: QuestionBase<any>[];
  allPropertiesList: PropertyItem[];
 
  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private questionService: QuestionService,
    private propertyInstanceService: PropertyInstanceService,
    private propertyDefinitionService: PropertyDefinitionService,
    private dialogFactory: DialogFactoryComponent
    ) {
      this.isLoaded = false;
    }

  ngOnInit() {
    
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant),
    ]).then(() => {
      this.route.params.subscribe(params => this.loadPropertiesFromTemplate(params['marketplaceId'], params['templateId']));
    })
  }

  loadPropertiesFromTemplate(marketplaceId: string, templateId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.getTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;    
     
      })
      .then(() => {
        this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
          this.allPropertiesList = propertyDefinitions;
        
        
        })
        .then(() => {
          if (!isNullOrUndefined(this.template)) {
            this.questions = this.questionService.getQuestionsFromProperties(this.template.templateProperties);
          }
          this.isLoaded = true;
        })
      });
    });  
  }


  loadAllPropertyDefinitions() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
      this.allPropertiesList = propertyDefinitions;
    });
  }


  navigateBack() {
    window.history.back();
  }

  navigateEditForm() {
    this.router.navigate([`/main/task-templates/user/edit/${this.marketplace.id}/${this.template.id}`], {queryParams: {ref: 'single'}});
  }

  deleteTemplate() {
    this.dialogFactory.confirmationDialog(
      "Are you sure?", "Are you sure you want to delete this Template? This action cannot be reverted")
      .then((cont: boolean) => {
        if (cont) {
          this.userDefinedTaskTemplateService.deleteRootTaskTemplate(this.marketplace, this.template.id).toPromise().then( (success: boolean) => {
            this.navigateBack();
          });
        }
      });
    }

    

  addPropertyDialog() {

    this.dialogFactory.addPropertyDialog(this.template, this.allPropertiesList).then((propIds: string[]) => {   
      if (!isNullOrUndefined(propIds)) {
        this.userDefinedTaskTemplateService.addPropertiesToSingleTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
          this.refresh();
        });
      }
    });
  }
  
  removePropertyDialog() {
    this.dialogFactory.removePropertyDialog(this.template).then((propIds: string[]) => {
      if (!isNullOrUndefined(propIds)) {
        this.userDefinedTaskTemplateService.removePropertiesFromSingleTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
          this.refresh();
        });
      }
    });
    
  }

  //TODO
  changePropertyOrderDialog() {

    this.dialogFactory.changePropertyOrderDialog(this.template.templateProperties).then((data: SortDialogData) => {
      if (!isNullOrUndefined(data)) {
        for (let i = 0; i<data.list.length; i++) {
          data.list[i].order = i;
        }

        this.userDefinedTaskTemplateService.updatePropertyOrderSingle(this.marketplace, this.template.id, data.list).toPromise().then((ret: UserDefinedTaskTemplate) => {
          this.refresh();
        });

      }  

    });
  }

  editDescriptionDialog() {

    this.dialogFactory.editTemplateDescriptionDialog(this.template).then((description: string) => {
      if (!isNullOrUndefined(description)) {
        this.userDefinedTaskTemplateService.updateRootTaskTemplate(this.marketplace, this.template.id, null, description).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template.description = updatedTemplate.description;
        });
      }
    });
  }
  
  editNameDialog() {

    this.dialogFactory.editTemplateNameDialog(this.template).then((name: string) => {
      if (!isNullOrUndefined(name)) {
        this.userDefinedTaskTemplateService.updateRootTaskTemplate(this.marketplace, this.template.id, name, null).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template.name = updatedTemplate.name;
        });
      }
    });
  }

  private refresh() {
    this.isLoaded = false;
    this.ngOnInit();
  }
}



