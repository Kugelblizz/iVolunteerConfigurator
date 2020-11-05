import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
import { PropertyConstraint } from 'app/main/content/_model/meta/constraint';
import { environment } from "environments/environment";


@Injectable({
  providedIn: 'root'
})
export class FlatPropertyDefinitionService {


  constructor(
    private http: HttpClient
  ) { }


  getAllPropertyDefinitons() {
    return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/flat/all`);
  }

  getPropertyDefinitionById(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/flat/${id}`);
  }

  createNewPropertyDefinition(propertyDefinitions: FlatPropertyDefinition<any>[]) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/property-definition/flat/new`, propertyDefinitions);
  }

  updatePropertyDefintion(propertyDefinitions: FlatPropertyDefinition<any>[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/property-definition/flat/update`, propertyDefinitions);
  }

  deletePropertyDefinition(id: string) {
    return this.http.delete(`${environment.CONFIGURATOR_URL}/property-definition/flat/${id}/delete`);
  }

  // addConstraintToPropertyDefinition(id: string, constraints: PropertyConstraint<any>[]) {
  //   return this.http.patch(`${environment.CONFIGURATOR_URL}/property-definition/flat/${id}/add-constraint`, constraints);
  // }

  // removeConstraintFromPropertyDefintion(id: string, constraintIds: string[]) {
  //   return this.http.patch(`${environment.CONFIGURATOR_URL}/property-definition/flat/${id}/remove-constraint`, constraintIds);
  // }





}
