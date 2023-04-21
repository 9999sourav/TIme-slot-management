import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InterviewerService {

  constructor(private httpClient : HttpClient) { }
  // baseUrl='https://taimusurotto.stackroute.io' +'/slot-management-service'+'/interviewer/'
  baseUrl = "http://localhost:8080/interviewer/"
  interviewer_id = localStorage.getItem("Id")

  getAllSlotsForInterviewer(interviewer_id:any){
    console.log(this.interviewer_id)
      return this.httpClient.get(this.baseUrl+"allSlots/"+this.interviewer_id);
  }

  getSlotsForDate(date:any){
    return this.httpClient.get(this.baseUrl+'getSlots/'+date+"/"+this.interviewer_id);
  }

  markAviailability(slot_ids:any){
    return this.httpClient.post(this.baseUrl+'addslots/'+this.interviewer_id,slot_ids);
  }

  cancelSlot(slot_id:any,reason:any){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("slot_id",slot_id);
    queryParams = queryParams.append("reason",reason);
    return this.httpClient.post(this.baseUrl+'cancelSlot/'+this.interviewer_id,queryParams);
  }


  

}
