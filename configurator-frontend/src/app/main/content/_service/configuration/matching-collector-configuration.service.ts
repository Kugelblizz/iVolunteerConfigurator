import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatchingConfiguration } from '../../_model/meta/configurations';
import { environment } from "environments/environment";

@Injectable({
    providedIn: 'root'
})

export class MatchingEntityDataService {
    constructor(
        private http: HttpClient
    ) { }

    // aggregateClassDefinitionsInSingleMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-single`);
    // }

    // aggregateClassDefinitionsInMultipleCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/aggregate-in-collections`);
    // }

    getMatchingData(matchingConfiguration: MatchingConfiguration) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/matching-entity-data/`, matchingConfiguration);
    }

    // getSavedMatchingCollectorConfiguration(marketplace: Marketplace, classConfiguratorId: string) {
    //     return this.http.get(`${marketplace.url}/matching-collector-configuration/${classConfiguratorId}/saved-configuration`);
    // }

    // createMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingEntityMappings) {
    //     return this.http.post(`${marketplace.url}/matching-collector-configuration/new`, matchingCollectorConfiguration);
    // }

    // updateMatchingCollectorConfiguration(marketplace: Marketplace, matchingCollectorConfiguration: MatchingEntityMappings) {
    //     return this.http.put(`${marketplace.url}/matching-collector-configuration/update`, matchingCollectorConfiguration);
    // }

    // deleteMatchingCollectorConfiguration(marketplace: Marketplace, id: string) {
    //     return this.http.delete(`${marketplace.url}/matching-collector-configuration/${id}/delete`);
    // }






}
