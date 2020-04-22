import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

import { Marketplace } from "../../../../_model/marketplace";

import { LoginService } from "../../../../_service/login.service";
import { ParticipantRole } from "../../../../_model/participant";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import {
  DerivationRule,
  MappingOperatorType,
  AttributeSourceRuleEntry,
  ClassSourceRuleEntry
} from "../../../../_model/derivation-rule";
import { DerivationRuleService } from "../../../../_service/derivation-rule.service";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import { ClassDefinitionService } from "../../../../_service/meta/core/class/class-definition.service";
import { ClassDefinition } from "../../../../_model/meta/Class";
import { ClassProperty } from "../../../../_model/meta/Property";
import { Helpseeker } from "../../../../_model/helpseeker";

@Component({
  templateUrl: "./rule-configurator.component.html",
  styleUrls: ["./rule-configurator.component.scss"],
  providers: []
})
export class FuseRuleConfiguratorComponent implements OnInit {
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  role: ParticipantRole;
  ruleForm: FormGroup;

  derivationRule: DerivationRule;

  fahrtenspangeImg = null;

  classDefinitions: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private classDefinitionService: ClassDefinitionService
  ) {
    this.ruleForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined),
      attributeSources: new FormControl(undefined),
      classSources: new FormControl(undefined),
      target: new FormControl(undefined)
    });
  }

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: Helpseeker) => {
        this.helpseeker = helpseeker;

        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;

            this.route.params.subscribe(params =>
              this.loadDerivationRule(marketplace, params["ruleId"])
            );
            this.classDefinitionService
              .getAllClassDefinitionsWithoutHeadAndEnums(
                marketplace,
                this.helpseeker.tenantId
              )
              .toPromise()
              .then(
                (definitions: ClassDefinition[]) =>
                  (this.classDefinitions = definitions)
              );
          });
      });
  }

  private loadDerivationRule(marketplace: Marketplace, ruleId: string) {
    if (ruleId) {
      this.derivationRuleService
        .findById(marketplace, ruleId, this.helpseeker.tenantId)
        .toPromise()
        .then((rule: DerivationRule) => {
          this.derivationRule = rule;
          this.ruleForm.setValue({
            id: this.derivationRule.id,
            name: this.derivationRule.name,
            attributeSources: this.derivationRule.attributeSourceRules,
            classSources: this.derivationRule.classSourceRules,
            target: this.derivationRule.target
          });
        });
    } else {
      this.derivationRule = new DerivationRule();
      this.derivationRule.attributeSourceRules = [
        <AttributeSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          classProperty: new ClassProperty(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: ""
        }
      ];
      this.derivationRule.classSourceRules = [
        <ClassSourceRuleEntry>{
          classDefinition: new ClassDefinition(),
          mappingOperatorType: MappingOperatorType.EQ,
          value: ""
        }
      ];
    }
  }

  save() {
    this.derivationRule.name = this.ruleForm.value.name;
    this.derivationRule.target = this.ruleForm.value.target;
    this.derivationRule.tenantId = this.helpseeker.tenantId;
    this.derivationRuleService
      .save(this.marketplace, this.derivationRule)
      .toPromise()
      .then(() =>
        this.loadDerivationRule(this.marketplace, this.derivationRule.id)
      );
  }

  navigateBack() {
    window.history.back();
  }

  addAttributeRule() {
    this.derivationRule.attributeSourceRules.push(
      new AttributeSourceRuleEntry()
    );
  }

  addClassRule() {
    this.derivationRule.classSourceRules.push(new ClassSourceRuleEntry());
  }
}