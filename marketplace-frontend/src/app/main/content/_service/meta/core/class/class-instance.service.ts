import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { ResponseService } from '../../../response.service';
import { isNullOrUndefined } from 'util';

@Injectable({
  providedIn: 'root'
})
export class ClassInstanceService {
  constructor(private http: HttpClient,
    private responseService: ResponseService) { }

  createNewClassInstances(classInstances: ClassInstance[], redirectUrl: string) {
    // return this.http.post(
    //   `${environment.CONFIGURATOR_URL}/class/instance/new`, classInstances
    // );

    if (isNullOrUndefined(classInstances) || classInstances.length <= 0) {
      return null;
    }

    const classInstance = classInstances.pop();
    this.responseService.sendClassInstanceConfiguratorResponse(redirectUrl, classInstance).toPromise().then(() => {
      return classInstance;
    });
  }


}
