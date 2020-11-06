import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, ControlContainer, FormGroupDirective, FormArray, Validators } from '@angular/forms';
import { ComparisonOperatorType, GeneralCondition } from 'app/main/content/_model/derivation-rule';
import { DerivationRuleService } from 'app/main/content/_service/derivation-rule.service';
import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
import { User } from '../../../../_model/user';
import { Tenant } from 'app/main/content/_model/tenant';

let output = console.log;

@Component({
  selector: "general-precondition",
  templateUrl: './general-precondition-configurator.component.html',
  styleUrls: ['../rule-configurator.component.scss'],
  viewProviders: [
    { provide: ControlContainer, useExisting: FormGroupDirective },
  ],
})
export class GeneralPreconditionConfiguratorComponent implements OnInit {
  @Input('generalCondition') generalCondition: GeneralCondition;
  @Output('generalConditionChange') generalConditionChange: EventEmitter<GeneralCondition> = new EventEmitter<GeneralCondition>();

  tenantAdmin: User;
  tenant: Tenant;
  rulePreconditionForm: FormGroup;
  genConditionForms: FormArray;
  comparisonOperators: any;
  generalAttributes: FlatPropertyDefinition<any>[];

  constructor(
    private formBuilder: FormBuilder,
    private derivationRuleService: DerivationRuleService,
    private parentForm: FormGroupDirective
  ) {
    this.rulePreconditionForm = formBuilder.group({
      propertyDefinitionId: new FormControl(undefined, [Validators.required]),
      comparisonOperatorType: new FormControl(undefined, Validators.required),
      value: new FormControl(undefined, [Validators.required]),
    });
  }

  async ngOnInit() {
    this.genConditionForms = <FormArray>(
      this.parentForm.form.controls['genConditionForms']
    );
    this.genConditionForms.push(this.rulePreconditionForm);

    this.rulePreconditionForm.setValue({
      propertyDefinitionId:
        (this.generalCondition.propertyDefinition
          ? this.generalCondition.propertyDefinition.id
          : '') || '',
      comparisonOperatorType:
        this.generalCondition.comparisonOperatorType || '',
      // ComparisonOperatorType.GE,
      value: this.generalCondition.value || '',
    });

    this.comparisonOperators = Object.keys(ComparisonOperatorType);

    this.derivationRuleService
      .getGeneralProperties(null, this.tenant.id)
      .toPromise()
      .then((genProperties: FlatPropertyDefinition<any>[]) => {
        this.generalAttributes = genProperties;
      });
  }

  onPropertyChange(propertyDefinition: FlatPropertyDefinition<any>, $event) {
    if (
      $event.isUserInput &&
      (!this.generalCondition.propertyDefinition ||
        this.generalCondition.propertyDefinition.id != propertyDefinition.id)
    ) {
      this.generalCondition.propertyDefinition = propertyDefinition;
      this.rulePreconditionForm.value.propertyDefinitionId =
        propertyDefinition.id;
      this.generalConditionChange.emit(this.generalCondition);
    }
  }

  onOperatorChange(comparisonOperatorType, $event) {
    if ($event.isUserInput) {
      // ignore on deselection of the previous option
      this.generalCondition.comparisonOperatorType = comparisonOperatorType;
      this.generalConditionChange.emit(this.generalCondition);
    }
  }

  onChange($event) {
    /* this.generalCondition.propertyDefinition = this.generalAttributes.find(
        (pd) => pd.id === this.rulePreconditionForm.value.propertyDefinitionId
      );*/
    // this.generalCondition.comparisonOperatorType = this.rulePreconditionForm.value.comparisonOperatorType;
    this.generalCondition.value = this.rulePreconditionForm.value.value;
    this.generalConditionChange.emit(this.generalCondition);
  }

  private retrieveComparisonOperatorValueOf(op) {
    const x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }
}
