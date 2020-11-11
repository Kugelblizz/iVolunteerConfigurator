import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ClassInstance } from '../_model/meta/class';

@Injectable({
  providedIn: 'root'
})
export class ResponseService {

  constructor(
    private http: HttpClient
  ) { }

  public sendClassConfiguratorResponse(url: string, id: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-configurator`, { url, id });
  }

  public sendClassInstanceConfiguratorResponse(url: string, classInstance: ClassInstance) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/class-instance-configurator`, { url, classInstance });
  }

  public sendMatchingConfiguratorResponse(url: string, id: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/send-response/matching-configurator`, { url, id });
  }



}
