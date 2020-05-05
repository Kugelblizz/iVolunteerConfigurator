import { Component, OnInit } from '@angular/core';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { ClassInstanceDTO } from 'app/main/content/_model/meta/class';
import { Tenant } from 'app/main/content/_model/tenant';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { CoreVolunteerService } from 'app/main/content/_service/core-volunteer.service';
import { StoredChartService } from 'app/main/content/_service/stored-chart.service';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { LocalRepositoryService } from 'app/main/content';
import { timer } from 'rxjs';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { StoredChart } from 'app/main/content/_model/stored-chart';
import { isNullOrUndefined } from 'util';


@Component({
  selector: 'fuse-management-summary',
  templateUrl: './management-summary.component.html',
  styleUrls: ['./management-summary.component.scss']
})
export class ManagementSummaryComponent implements OnInit {

  // comparison chart
  comparisonXlabel = 'Jahr';
  comparisonYlabel = 'Anzahl Tätigkeiten';
  colorScheme = 'cool';

  schemeType = 'ordinal';
  showGridLines = true;
  animations = true;
  gradient = false;
  showXAxis = true;
  showYAxis = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  xAxisLabel = 'Datum';
  yAxisLabel = 'Dauer [Stunden]';
  noBarWhenZero = true;
  showLabels = true;
  autoScale = true;
  legend = false;
  legendPosition = 'below';
  tooltipDisabled = false;

  volunteer: Volunteer;
  marketplace: any = [];
  classInstanceDTOs: ClassInstanceDTO[] = [];

  uniqueYears: any[] = [];
  tenantMap: Map<String, Tenant>;

  durationTotal: any[] = [];
  numberTotal: any[] = [];

  durationYear: any[] = [];
  numberYear: any[] = [];

  comparisonData: any[] = [];
  comparisonYear: number;
  engagementYear: number;

  isLocalRepositoryConnected: boolean;
  timeout: boolean = false;

  percentageFilteredOut: number = 0;

  constructor(
    private loginService: LoginService,
    private classInstanceService: ClassInstanceService,
    private volunteerService: CoreVolunteerService,
    private storedChartService: StoredChartService,
    private tenantService: TenantService,
    private localRepositoryService: LocalRepositoryService
  ) { }

  async ngOnInit() {
    let t = timer(3000);
    t.subscribe(() => {
      this.timeout = true;
    });

    this.comparisonYear = 2019;
    this.engagementYear = 2019;


    this.volunteer = <Volunteer>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.isLocalRepositoryConnected = await this.localRepositoryService.isConnected(this.volunteer);

    let marketplaces = <Marketplace[]>(
      await this.volunteerService
        .findRegisteredMarketplaces(this.volunteer.id)
        .toPromise()
    );
    // TODO for each registert mp
    this.marketplace = marketplaces[0];

    if (this.isLocalRepositoryConnected) {
      this.classInstanceDTOs = <ClassInstanceDTO[]>(
        await this.localRepositoryService.findClassInstancesByVolunteer(this.volunteer).toPromise());

    } else {
      if (!isNullOrUndefined(this.marketplace)) {
        this.classInstanceDTOs = <ClassInstanceDTO[]>(
          await this.classInstanceService
            .getUserClassInstancesByArcheType(
              this.marketplace,
              "TASK",
              this.volunteer.id,
              this.volunteer.subscribedTenants
            )
            .toPromise()
        );
      }
    }

    // filter out classInstances missing the reqired fields
    let before = this.classInstanceDTOs.length;
    this.classInstanceDTOs = this.classInstanceDTOs.filter(ci => {
      return (ci.name != null && ci.tenantId != null && ci.dateFrom && ci.taskType1 && ci.duration)
    });
    let after = this.classInstanceDTOs.length;
    this.percentageFilteredOut = (1-(after/before))*100;

    
    this.uniqueYears = [...new Set(this.classInstanceDTOs.map(item => new Date(item.dateFrom).getFullYear()))];
    
    let uniqueTenants = [...new Set(this.classInstanceDTOs.map(item => item.tenantId))];
    this.tenantMap = new Map<String, Tenant>();
    for (let tenantId of uniqueTenants) {
      let tenant = <Tenant>await this.tenantService.findById(tenantId).toPromise();
      this.tenantMap.set(tenantId, tenant);
    }

    this.generateComparisonChartData(this.comparisonYear);
    this.generateEngagementYearData(this.engagementYear);
    this.generateEngagementTotalData();

  }

  generateComparisonChartData(comparisonYear) {
    this.tenantMap.forEach(tenant => {

      let yearData = this.classInstanceDTOs.filter(ci => {
        return (new Date(ci.dateFrom).getFullYear() === comparisonYear);
      }).filter(ci => {
        return (ci.tenantId === tenant.id);
      })

      this.comparisonData.push({ name: tenant.name, value: yearData.length });
    });

    let finalData: any[] = [];

    this.uniqueYears.sort();
    this.uniqueYears.forEach(curYear => {
      let data: any[] = [];
      this.tenantMap.forEach(tenant => {
        let currentData = this.classInstanceDTOs.filter(ci => {
          return new Date(ci.dateFrom).getFullYear() === curYear;
        }).filter(ci => {
          return ci.tenantId === tenant.id;
        })

        data.push({ name: tenant.name, value: currentData.length - this.comparisonData.find(d => d.name === tenant.name).value });
      });
      finalData.push({ name: curYear.toString(), series: data });
    });

    this.comparisonData = [...finalData];
  }

  generateEngagementYearData(engagementYear) {
    this.engagementYear = engagementYear;
    let classInstancesYear = this.classInstanceDTOs.filter(ci => {
      return new Date(ci.dateFrom).getFullYear() === this.engagementYear;
    });

    this.durationYear = [];
    this.numberYear = [];

    this.tenantMap.forEach(tenant => {
      let classInstancesTenant = classInstancesYear.filter(ci => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce((acc, curr) => acc + Number(curr.duration), 0);
      this.durationYear.push({ name: tenant.name, value: duration });
      this.numberYear.push({ name: tenant.name, value: classInstancesTenant.length });
    });

    this.durationYear = [... this.durationYear];
    this.numberYear = [... this.numberYear];
  }

  generateEngagementTotalData() {
    this.tenantMap.forEach(tenant => {
      let classInstancesTenant = this.classInstanceDTOs.filter(ci => {
        return ci.tenantId === tenant.id;
      });

      let duration = classInstancesTenant.reduce((acc, curr) => acc + Number(curr.duration), 0)
      this.durationTotal.push({ name: tenant.name, value: duration });
      this.numberTotal.push({ name: tenant.name, value: classInstancesTenant.length });
    });


  }

  exportChart(source: string) {
    let storedChart: StoredChart;

    switch (source) {
      case 'Vergleich':
        storedChart = new StoredChart('Vergleich der Anzahl an Tätigkeiten', 'ngx-charts-bar-vertical-2d', JSON.stringify(this.comparisonData), this.volunteer.id);
        this.storedChartService.save(this.marketplace, storedChart).toPromise();
        break;
    }
  }

  valueFormattingDuration(c) {
    return `${(c)} Stunden`;
  }

  valueFormattingNumber(c) {
    return `${(c)} Tätigkeiten`;
  }

  getMemberSince(tenantId) {
    let t = this.classInstanceDTOs.filter(ci => {
      return ci.tenantId === tenantId
    });
    let uniqueYears = [...new Set(t.map(item => new Date(item.dateFrom).getFullYear()))];
    return Math.min.apply(Math, uniqueYears);
  }

  getEngagementSince() {
    let uniqueYears = [...new Set(this.classInstanceDTOs.map(item => new Date(item.dateFrom).getFullYear()))];
    return Math.min.apply(Math, uniqueYears) + ' - ' + Math.max.apply(Math, uniqueYears);
  }
}