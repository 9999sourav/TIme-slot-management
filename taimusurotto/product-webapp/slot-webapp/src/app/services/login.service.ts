import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient : HttpClient) { }
  // baseUrl='https://taimusurotto.stackroute.io' +'/slot-management-service'+'/login/'
  baseUrl = 'http://localhost:8080/login/'

  login(data:any){
    return this.httpClient.post(this.baseUrl+"verifyCreds",data);
  }

  resetPassword(data:any){
    let id=localStorage.getItem("Id")
    let role=localStorage.getItem('Role')
    return this.httpClient.post(this.baseUrl+'resetPassword/'+id+"/"+role,data);
  }

}
