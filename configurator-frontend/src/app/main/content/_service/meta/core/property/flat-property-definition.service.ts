import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
import { environment } from 'environments/environment';


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

  getAllPropertyDefinitonsForTenant(tenantId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/flat/all/tenant/${tenantId}`);
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

}
