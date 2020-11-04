import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { fuseAnimations } from '@fuse/animations';
import { DerivationRule, ComparisonOperatorType, AggregationOperatorType } from '../../../../_model/derivation-rule';
import { DerivationRuleService } from '../../../../_service/derivation-rule.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { User } from 'app/main/content/_model/user';

@Component({
  selector: 'fuse-rule-overview',
  templateUrl: './rule-overview.component.html',
  styleUrls: ['./rule-overview.component.scss'],
  animations: fuseAnimations,
})
export class FuseRuleOverviewComponent implements OnInit {
  dataSource = new MatTableDataSource<DerivationRule>();
  displayedColumns = ['active', 'name', 'sourcesGeneral', 'sourcesClasses', 'target'];
  tenantAdmin: User;
  tenant: Tenant;

  constructor(
    private router: Router,
    private derivationRuleService: DerivationRuleService
  ) { }

  async ngOnInit() {
    this.loadAllDerivationRules();
  }

  onRowSelect(derivationRule: DerivationRule, event) {
    this.router.navigate(['/main/rule/' + derivationRule.id]);
  }

  onChangeActivation(derivationRule: DerivationRule) {
    this.derivationRuleService.save(null, derivationRule).toPromise();
    // this.loadAllDerivationRules();
  }

  private async loadAllDerivationRules() {
    this.derivationRuleService.findAll(null, this.tenant.id).toPromise().then((rules: DerivationRule[]) => (this.dataSource.data = rules));
  }

  addDerivationRule() {
    this.router.navigate(['/main/rule']);
  }

  private retrieveComparisonOperatorValueOf(op) {
    const x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }

  private retrieveAggregationOperatorValueOf(op) {
    const x: AggregationOperatorType =
      AggregationOperatorType[op as keyof typeof AggregationOperatorType];
    return x;
  }
}
