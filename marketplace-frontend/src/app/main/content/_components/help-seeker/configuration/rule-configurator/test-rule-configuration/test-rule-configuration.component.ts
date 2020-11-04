import { Component, OnInit, Input } from '@angular/core';
import { User } from 'app/main/content/_model/user';
import { MatTableDataSource } from '@angular/material/table';
import { Tenant } from 'app/main/content/_model/tenant';
import { RuleExecution, RuleStatus } from 'app/main/content/_model/derivation-rule-execution';
import { DerivationRule, ClassCondition } from 'app/main/content/_model/derivation-rule';
import { DerivationRuleService } from 'app/main/content/_service/derivation-rule.service';

@Component({
  selector: "test-rule-configuration",
  templateUrl: './test-rule-configuration.component.html',
  styleUrls: ['./test-rule-configuration.component.scss'],
})
export class TestRuleConfigurationComponent implements OnInit {
  @Input('derivationRule') derivationRule: DerivationRule;
  dataSource = new MatTableDataSource<RuleExecution>();
  classConditions: ClassCondition[];
  ruleExecutions: RuleExecution[];
  displayedColumns = ['Name', 'Status'];
  tenantAdmin: User;
  tenant: Tenant;

  constructor(
    private derivationRuleService: DerivationRuleService
  ) { }

  async ngOnInit() {
    this.testRule();
  }

  private testRule() {
    this.derivationRule.container =
      'simulate execution ' + this.derivationRule.name;
    this.derivationRuleService
      .test(null, this.derivationRule)
      .toPromise()
      //  .then(() => this.derivationRuleService.getTestResults(this.marketplace, this.derivationRule)
      //  .toPromise()
      .then((ruleExecutions: RuleExecution[]) => {
        this.dataSource.data = ruleExecutions;
      });
  }

  private retrieveStatusValueOf(status) {
    const x: RuleStatus = RuleStatus[status as keyof typeof RuleStatus];
    return x;
  }
}
