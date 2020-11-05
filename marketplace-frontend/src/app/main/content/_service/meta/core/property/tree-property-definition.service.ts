import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';
import { isNullOrUndefined } from 'util';
import { environment } from "environments/environment";


@Injectable({
    providedIn: 'root',
})
export class TreePropertyDefinitionService {
    constructor(private http: HttpClient) { }

    getAllPropertyDefinitions(marketplace: Marketplace) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/all`);
    }

    getAllPropertyDefinitionsForTenant() {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/all`);
    }

    getPropertyDefinitionById(id: string) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/${id}`);
    }

    getPropertyDefinitionByName(name: string) {
        return this.http.get(`${environment.CONFIGURATOR_URL}/property-definition/tree/by-name/${name}`);
    }

    newPropertyDefinition(treePropertyDefinition: TreePropertyDefinition) {
        return this.http.post(`${environment.CONFIGURATOR_URL}/property-definition/tree/new`, treePropertyDefinition);
    }

    savePropertyDefinition(treePropertyDefinition: TreePropertyDefinition) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/property-definition/tree/save`, treePropertyDefinition);
    }

    deletePropertyDefinition(id: string) {
        return this.http.delete(`${environment.CONFIGURATOR_URL}/property-definition/tree/${id}/delete`);
    }

    deletePropertyDefinitions(ids: string[]) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/property-definition/tree/delete-multiple`, ids);
    }

}
