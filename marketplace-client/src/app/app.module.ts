import {NgModule} from '@angular/core';
import {AppMaterialModule} from './app-material.module';
import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';

import {TaskService} from './task/task.service';
import {TaskTypeService} from './task-type/task-type.service';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';

import {NavbarComponent} from './navbar/navbar.component';
import {TaskDetailsComponent} from './task/details/task-details.component';
import {TaskInteractionService} from './task-interaction/task-interaction.service';
import {TokenInterceptor} from './interceptor/token.interceptor';
import {Http401Interceptor} from './interceptor/http-401.interceptor';
import {LoginService} from './login/login.service';
import {LoginComponent} from './login/login.component';
import {LoginGuard} from './login/login.guard';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    TaskListComponent,
    TaskCreateComponent,
    TaskDetailsComponent,
    TaskTypeListComponent,
    TaskTypeCreateComponent
  ],
  imports: [
    AppMaterialModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    LoginGuard,
    LoginService,
    TaskService,
    TaskTypeService,
    TaskInteractionService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}

