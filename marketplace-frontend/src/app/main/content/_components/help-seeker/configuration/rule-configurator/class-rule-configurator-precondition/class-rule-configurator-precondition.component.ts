import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";
import { User, ParticipantRole } from "../../../../../_model/user";
import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  MappingOperatorType,
  AttributeSourceRuleEntry,
  ClassSourceRuleEntry,
  ClassAggregationOperatorType,
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";

@Component({
  selector: "class-rule-precondition",
  templateUrl: "./class-rule-configurator-precondition.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class FuseClassRulePreconditionConfiguratorComponent implements OnInit {
  @Input("classSourceRuleEntry") classSourceRuleEntry: ClassSourceRuleEntry;
  @Output("classSourceRuleEntry") classSourceRuleEntryChange: EventEmitter<
    ClassSourceRuleEntry
  > = new EventEmitter<ClassSourceRuleEntry>();

  helpseeker: User;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  comparisonOperators: any;
  aggregationOperators: any;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private helpSeekerService: CoreHelpSeekerService
  ) {
    this.rulePreconditionForm = formBuilder.group({
      classDefinitionId: new FormControl(undefined),
      aggregationOperatorType: new FormControl(undefined),
      mappingOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      classDefinitionId:
        (this.classSourceRuleEntry.classDefinition
          ? this.classSourceRuleEntry.classDefinition.id
          : "") || "",
      aggregationOperatorType:
        this.classSourceRuleEntry.aggregationOperatorType ||
        ClassAggregationOperatorType.COUNT,
      mappingOperatorType:
        this.classSourceRuleEntry.mappingOperatorType || MappingOperatorType.EQ,
      value: this.classSourceRuleEntry.value || "",
    });

    this.comparisonOperators = Object.keys(MappingOperatorType);
    this.aggregationOperators = Object.keys(ClassAggregationOperatorType);

    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: User) => {
        this.helpseeker = helpseeker;
        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;
            this.classDefinitionService
              .getAllClassDefinitionsWithoutHeadAndEnums(
                marketplace,
                // TODO Philipp
                this.helpseeker.subscribedTenants.map((s) => s.tenantId)[0]
              )
              .toPromise()
              .then((definitions: ClassDefinition[]) => {
                this.classDefinitions = definitions;
                this.loadClassProperties(null);
              });
          });
      });
  }

  onClassChange($event) {
    if (!this.classSourceRuleEntry.classDefinition) {
      this.classSourceRuleEntry.classDefinition = new ClassDefinition();
    }
    this.classSourceRuleEntry.classDefinition.id = $event.source.value;
    this.classSourceRuleEntry.classDefinition.tenantId = this.helpseeker.subscribedTenants.map(
      (s) => s.tenantId
    )[0];

    this.loadClassProperties($event);
  }

  private loadClassProperties($event) {
    if (
      this.classSourceRuleEntry &&
      this.classSourceRuleEntry.classDefinition &&
      this.classSourceRuleEntry.classDefinition.id
    ) {
      this.classPropertyService
        .getAllClassPropertiesFromClass(
          this.marketplace,
          this.classSourceRuleEntry.classDefinition.id
        )
        .toPromise()
        .then((props: ClassProperty<any>[]) => {
          this.classProperties = props;
          this.onChange($event);
        });
    }
  }

  onChange($event) {
    if (this.classDefinitions.length > 0 && this.classProperties.length > 0) {
      this.classSourceRuleEntry.classDefinition = this.classDefinitions.find(
        (cd) => cd.id === this.rulePreconditionForm.value.classDefinitionId
      );
      this.classSourceRuleEntry.mappingOperatorType = this.rulePreconditionForm.value.mappingOperatorType;
      this.classSourceRuleEntry.aggregationOperatorType = this.rulePreconditionForm.value.aggregationOperatorType;
      this.classSourceRuleEntry.value = this.rulePreconditionForm.value.value;
      this.classSourceRuleEntryChange.emit(this.classSourceRuleEntry);
    }
  }

  private retrieveMappingOperatorValueOf(op) {
    let x: MappingOperatorType =
      MappingOperatorType[op as keyof typeof MappingOperatorType];
    return x;
  }

  private retrieveAggregationOperatorValueOf(op) {
    let x: ClassAggregationOperatorType =
      ClassAggregationOperatorType[
        op as keyof typeof ClassAggregationOperatorType
      ];
    return x;
  }
}
