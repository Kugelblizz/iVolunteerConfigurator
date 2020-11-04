import { FuseSharedModule } from '@fuse/shared.module';
import { FuseContentComponent } from 'app/main/content/content.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AnonymGuard } from './_guard/anonym.guard';
import { Route, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Route[] = [

  {
    path: 'main/dashboard',
    loadChildren: () =>
      import('./_components/common/dashboard/dashboard.module').then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/properties/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-configurator/list/property-list.module'
      ).then((m) => m.PropertyListModule),
    canActivate: [AnonymGuard]
  },

  {
    path: 'main/property/detail/view',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-configurator/detail/property-detail.module'
      ).then((m) => m.PropertyDetailModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/property-builder',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-configurator/builder/property-build-form.module'
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/class-configurator',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/class-configurator/configurator.module'
      ).then((m) => m.ConfiguratorModule),
    canActivate: [AnonymGuard]
  },

  {
    path: 'main/instance-editor',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/class-instance-configurator/form-editor/class-instance-form-editor.module'
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/rules/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/rule-view/rule-overview.module'
      ).then((m) => m.FuseRuleOverviewModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/rule',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/rule-configurator/rule-configurator.module'
      ).then((m) => m.FuseRuleConfiguratorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/matching-configurator',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/matching-configurator/matching-configurator.module'
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [AnonymGuard]
  },
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
