import { Component, OnInit } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatTableDataSource } from '@angular/material';
import { ClassArchetype } from 'app/main/content/_model/meta/class';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { User, UserRole } from 'app/main/content/_model/user';
import { isNullOrUndefined } from 'util';

@Component({
  templateUrl: './task-select.component.html',
  styleUrls: ['./task-select.component.scss']
})
export class FuseTaskSelectComponent implements OnInit {
  dataSource = new MatTableDataSource<ClassDefinition>();
  displayedColumns = ['name', 'configuration'];
  user: User;
  userRole: UserRole;
  tenantId: string;

  constructor(
    formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService
  ) { }

  async ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId'])) {
        console.error('tenantId not set');
        this.router.navigate(['main/invalid-parameters']);
        // TODO redirect
      } else {
        console.log("tenantId set")
        this.tenantId = params['tenantId'];
      }
    });
    const tasks = <ClassDefinition[]>await this.classDefinitionService.getByArchetype(ClassArchetype.TASK, null).toPromise();

    this.dataSource.data = tasks
      .filter(t => t.configurationId != null)
      .sort((c1, c2) => c1.configurationId.localeCompare(c2.configurationId));

  }

  onRowSelect(row) {
    this.router.navigate([`main/instance-editor`], {
      queryParams: [row.id]
    });
  }
}
