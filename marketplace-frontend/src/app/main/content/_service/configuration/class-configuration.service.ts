import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassConfiguration } from '../../_model/meta/configurations';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassConfigurationService {

  constructor(
    private http: HttpClient
  ) { }

  getAllClassConfigurations() {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all`);
  }

  getAllClassConfigurationByTenantId(tenantId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all/tenant/${tenantId}`);
  }

  getAllClassConfigurationById(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/${id}`);
  }

  getClassConfigurationByName(name: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/by-name/${name}`);
  }

  // getClassConfigurationsByTenantId(marketplace: Marketplace, tenantId: string) {
  //   return this.http.get(`${marketplace.url}/class-configuration/all/tenant/${tenantId}`);

  // }

  getAllForClassConfigurationInOne(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all-in-one/${id}`);
  }

  createNewEmptyClassConfiguration(name: string, description: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/class-configuration/new-empty`, [name, description]);
  }

  createNewClassConfiguration(name: string, description: string) {
    const params: string[] = [];
    params.push(name);
    params.push(description);

    return this.http.post(`${environment.CONFIGURATOR_URL}/class-configuration/new`, params);
  }

  saveClassConfiguration(classConfiguration: ClassConfiguration) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/save`, classConfiguration);
  }


  saveClassConfigurationMeta(id: string, name: string, description: string) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/${id}/save-meta/`, [name, description]);
  }

  deleteClassConfiguration(id: string) {
    return this.http.delete(`${environment.CONFIGURATOR_URL}/class-configuration/${id}/delete`);
  }

  deleteClassConfigurations(ids: string[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/delete-multiple`, ids);
  }



}
