import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassInstanceService {
  constructor(private http: HttpClient) { }

  createNewClassInstances(classInstances: ClassInstance[]) {
    return this.http.post(
      `${environment.CONFIGURATOR_URL}/class/instance/new`, classInstances
    );
  }


}
