import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FreezeService {

  constructor(private httpClient: HttpClient) { }

  // baseUrl='https://taimusurotto.stackroute.io' +'/slot-management-service'+'/freeze'
  baseUrl = "http://localhost:8080/freeze/"

  saveFreezeTime(data:any){
    return this.httpClient.post(this.baseUrl+"freezeSlots",data);
  }

  updateFreezeTime(data:any){
    return this.httpClient.post(this.baseUrl+"updateFreezeSlots",data);
  }

}
