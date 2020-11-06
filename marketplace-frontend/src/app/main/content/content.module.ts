import { FuseSharedModule } from '@fuse/shared.module';
import { FuseContentComponent } from 'app/main/content/content.component';
import { AnonymGuard } from './_guard/anonym.guard';
import { Route, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Route[] = [

  {
    path: 'main/dashboard',
    loadChildren: () =>
      import('./_components/dashboard/dashboard.module').then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/properties/all',
    loadChildren: () =>
      import(
        './_components/configuration/property-configurator/list/property-list.module'
      ).then((m) => m.PropertyListModule),
    canActivate: [AnonymGuard]
  },

  {
    path: 'main/property/detail/view',
    loadChildren: () =>
      import(
        './_components/configuration/property-configurator/detail/property-detail.module'
      ).then((m) => m.PropertyDetailModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/property-builder',
    loadChildren: () =>
      import(
        './_components/configuration/property-configurator/builder/property-build-form.module'
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/class-configurator',
    loadChildren: () =>
      import(
        './_components/configuration/class-configurator/configurator.module'
      ).then((m) => m.ConfiguratorModule),
    canActivate: [AnonymGuard]
  },

  {
    path: 'main/instance-editor',
    loadChildren: () =>
      import(
        './_components/configuration/class-instance-configurator/form-editor/class-instance-form-editor.module'
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/rules/all',
    loadChildren: () =>
      import(
        './_components/configuration/rule-view/rule-overview.module'
      ).then((m) => m.FuseRuleOverviewModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/rule',
    loadChildren: () =>
      import(
        './_components/configuration/rule-configurator/rule-configurator.module'
      ).then((m) => m.FuseRuleConfiguratorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/matching-configurator',
    loadChildren: () =>
      import(
        './_components/configuration/matching-configurator/matching-configurator.module'
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/task-select',
    loadChildren: () =>
      import(
        './_components/task-management/task-select/task-select.module'
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [AnonymGuard],
  },
  {
    path: 'main/invalid-parameters',
    loadChildren: () =>
      import(
        './_components/_shared/invalid-parameters/invalid-parameters.module'
      ).then((m) => m.InvalidParametersModule),
    canActivate: [AnonymGuard],
  },
  // {
  //   path: 'main/tasks/all',
  //   loadChildren: () =>
  //     import(
  //       './_components/task-management/task-list/task-list.module'
  //     ).then((m) => m.FuseTaskListModule),
  //   canActivate: [AnonymGuard],
  // },
];

@NgModule({
  declarations: [FuseContentComponent],
  imports: [RouterModule.forChild(routes), FuseSharedModule],
  // providers: [
  //   { provide: HTTP_INTERCEPTORS, multi: true },
  // ],
  exports: [FuseContentComponent],
})
export class FuseContentModule { }
