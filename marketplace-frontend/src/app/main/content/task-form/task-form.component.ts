import {Component, OnInit} from '@angular/core';
import {isArray, isNullOrUndefined} from 'util';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Competence} from '../_model/competence';
import {Task} from '../_model/task';
import {TaskTemplate} from '../_model/task-template';
import {WorkflowType} from '../_model/workflow-type';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {CompetenceService} from '../_service/competence.service';
import {TaskTemplateService} from '../_service/task-template.service';
import {WorkflowService} from '../_service/workflow.service';
import {CompetenceValidator} from '../_validator/competence.validator';
import {Participant} from '../_model/participant';
import {CoreEmployeeService} from '../_service/core-employee.service';
import {Marketplace} from '../_model/marketplace';

@Component({
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class FuseTaskFormComponent implements OnInit {
  taskForm: FormGroup;
  competences: Array<Competence>;
  taskTemplates: Array<TaskTemplate>;
  workflowTypes: Array<WorkflowType>;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService,
              private taskService: TaskService,
              private competenceService: CompetenceService,
              private taskTemplateService: TaskTemplateService,
              private workflowService: WorkflowService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'workflowType': new FormControl(undefined, Validators.required),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([])
    }, {validator: CompetenceValidator});
  }


  isEditMode() {
    return !isNullOrUndefined(this.taskForm.value.id);
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        Promise.all([
          this.taskTemplateService.findAll(marketplace).toPromise().then((taskTemplates: Array<TaskTemplate>) => this.taskTemplates = taskTemplates),
          this.workflowService.findAllTypes(marketplace).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
          this.competenceService.findAll(marketplace).toPromise().then((competences: Array<Competence>) => this.competences = competences)
        ]).then(() => this.route.params.subscribe(params => this.findTask(marketplace, params['taskId'])));
      });
    });
  }

  private findTask(marketplace: Marketplace, taskId: string) {
    if (isNullOrUndefined(taskId) || taskId.length === 0) {
      return;
    }

    this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => {
      this.taskForm.setValue({
        id: task.id,
        name: task.name,
        description: task.description,
        workflowType: this.workflowTypes.find((value: WorkflowType) => task.workflowKey === value.key),
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate),
        requiredCompetences: this.competences.filter((competence: Competence) => {
          return task.requiredCompetences.find((requiredCompetence: Competence) => requiredCompetence.name === competence.name);
        }),
        acquirableCompetences: this.competences.filter((competence: Competence) => {
          return task.acquirableCompetences.find((acquirableCompetence: Competence) => acquirableCompetence.name === competence.name);
        })
      });
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    const task = this.taskForm.value;
    task.workflowKey = task.workflowType.key;
    delete task.workflowType;

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (this.isEditMode()) {
          this.taskService.save(marketplace, <Task>task).toPromise().then(() => this.router.navigate(['/tasks']));
        } else {
          Promise.all([
            this.loginService.getLoggedIn().toPromise(),
            this.taskService.save(marketplace, <Task>task).toPromise()
          ]).then((values: any[]) => {
            const createdTask = <Task> values[1];
            const participantId = (<Participant> values[0]).id;
            this.workflowService.startWorkflow(marketplace, createdTask.workflowKey, createdTask.id, participantId).toPromise().then(() => this.router.navigate(['/tasks']));
          });
        }
      });
    });
  }

  prefillForm(event, taskTemplate) {
    if (!event.source.selected) {
      return;
    }
    const currentValues = this.taskForm.value;
    currentValues.name = taskTemplate.name;
    currentValues.description = taskTemplate.description;
    currentValues.workflowType = this.workflowTypes.find((value: WorkflowType) => value.key === taskTemplate.workflowKey);

    if (isArray(taskTemplate.requiredCompetences)) {
      currentValues.requiredCompetences = this.competences.filter((competence: Competence) => {
        return taskTemplate.requiredCompetences.find((value: Competence) => value.name === competence.name);
      });
    } else {
      currentValues.requiredCompetences = [];
    }

    if (isArray(taskTemplate.acquirableCompetences)) {
      currentValues.acquirableCompetences = this.competences.filter((competence: Competence) => {
        return taskTemplate.acquirableCompetences.find((value: Competence) => value.name === competence.name);
      });
    } else {
      currentValues.acquirableCompetences = [];
    }

    this.taskForm.setValue(currentValues);
  }
}