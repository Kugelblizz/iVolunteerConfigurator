import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  ClassInstance,
  ClassDefinition,
} from "app/main/content/_model/meta/class";
import { Participant } from "app/main/content/_model/participant";

@Injectable({
  providedIn: "root",
})
export class ClassInstanceService {
  constructor(private http: HttpClient) {}

  getUserClassInstancesByArcheType(
    marketplace: Marketplace,
    archetype: string,
    userId: string,
    tenantIds: string[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}/user/${userId}`,
      tenantIds
    );
  }

  getClassInstanceById(marketplace: Marketplace, classInstanceId: String) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/${classInstanceId}`
    );
  }

  getClassInstancesInUserInbox(
    marketplace: Marketplace,
    issuerId: string,
    tenantIds: string[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/in-user-inbox/${issuerId}`,
      tenantIds
    );
  }

  getClassInstancesInIssuerInbox(
    marketplace: Marketplace,
    issuerId: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/in-issuer-inbox/${issuerId}?tId=${tenantId}`
    );
  }

  createNewClassInstances(
    marketplace: Marketplace,
    classInstances: ClassInstance[]
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/new`,
      classInstances
    );
  }

  createSharedClassInstances(
    marketplace: Marketplace,
    tenantId: string,
    classInstanceId: string
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/newShared?tId=${tenantId}`,
      classInstanceId
    );
  }

  createClassInstanceByClassDefinitionId(
    marketplace: Marketplace,
    classDefinitionId: ClassDefinition,
    volunteerId: Participant,
    tenantId: string,
    properties
  ) {
    return this.http.post(
      `${marketplace.url}/meta/core/class/instance/from-definition/${classDefinitionId}/user/${volunteerId}?tId=${tenantId}`,
      properties
    );
  }

  setClassInstanceInUserRepository(
    marketplace: Marketplace,
    classInstanceIds: string[],
    inUserRepository: boolean
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/instance/set-in-user-repository/${inUserRepository}`,
      classInstanceIds
    );
  }

  setClassInstanceInIssuerInbox(
    marketplace: Marketplace,
    classInstanceIds: string[],
    inIssuerInbox: boolean
  ) {
    return this.http.put(
      `${marketplace.url}/meta/core/class/instance/set-in-issuer-inbox/${inIssuerInbox}`,
      classInstanceIds
    );
  }

  getAllClassInstances(marketplace: Marketplace, tenantId: string) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/all?tId=${tenantId}`
    );
  }

  getClassInstancesByArcheType(
    marketplace: Marketplace,
    archetype: string,
    tenantId: string
  ) {
    return this.http.get(
      `${marketplace.url}/meta/core/class/instance/all/by-archetype/${archetype}?tId=${tenantId}`
    );
  }

  // updateClassInstance(marketplace: Marketplace, classInstance: ClassInstance) {
  //   return this.http.put(
  //     `${marketplace.url}/meta/core/class/instance/${classInstance.id}/update`,
  //     classInstance
  //   );
  // }

  deleteClassInstance(marketplace: Marketplace, classInstanceId: string) {
    return this.http.delete(
      `${marketplace.url}/meta/core/class/instance/${classInstanceId}/delete`
    );
  }
}
