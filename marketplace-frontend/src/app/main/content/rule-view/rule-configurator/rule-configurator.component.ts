import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CoreMarketplaceService } from '../../_service/core-marketplace.service';
import { Marketplace } from '../../_model/marketplace';

import { isNullOrUndefined } from 'util';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { MessageService } from '../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { FuseRulePreconditionConfiguratorComponent } from './rule-configurator-precondition/rule-configurator-precondition.component';
import { DerivationRule, SourceRuleEntry, MappingOperatorType } from '../../_model/derivation-rule';
import { DerivationRuleService } from '../../_service/derivation-rule.service';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';
import { ClassDefinitionService } from '../../_service/meta/core/class/class-definition.service';
import { ClassDefinition } from '../../_model/meta/Class';
import { ClassProperty } from '../../_model/meta/Property';

@Component({
  templateUrl: './rule-configurator.component.html',
  styleUrls: ['./rule-configurator.component.scss'],
  providers: [],
})
export class FuseRuleConfiguratorComponent implements OnInit {

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;

  classDefinitions: ClassDefinition[] = [];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService,
    private messageService: MessageService) {
    this.ruleForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'sources': new FormControl(undefined),
      'target': new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.route.params.subscribe(params => this.loadDerivationRule(marketplace, params['ruleId']));
        this.classDefinitionService.getAllClassDefinitions(marketplace).toPromise().then(
          (definitions: ClassDefinition[]) => this.classDefinitions = definitions
        );
      });
    });
  }

  private loadDerivationRule(marketplace: Marketplace, ruleId: string) {
    if (ruleId) {
      this.derivationRuleService.findById(marketplace, ruleId).toPromise().then(
        (rule: DerivationRule) => {
          this.derivationRule = rule;
          this.ruleForm.setValue({
            id: this.derivationRule.id,
            name: this.derivationRule.name,
            sources: this.derivationRule.sources,
            target: this.derivationRule.target
          });
        }
      );
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.sources = [<SourceRuleEntry>{
        classDefinition: new ClassDefinition(), 
        classProperty: new ClassProperty(), 
        mappingOperatorType: MappingOperatorType.EQ, 
        value: ""
      }];
    }
  }

  save() {
    this.derivationRule.name = this.ruleForm.value.name;
    this.derivationRule.target = this.ruleForm.value.target;
    // this.derivationRule.sources = this.ruleForm.value.sources;

    this.derivationRuleService.save(this.marketplace, this.derivationRule).toPromise().then(() => this.loadDerivationRule(this.marketplace, this.derivationRule.id));
  }

  navigateBack() {
    window.history.back();
  }
}