import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GraphService {
  values:any[]=[];

  constructor(private httpclient:HttpClient) { }
  // baseurl='https://taimusurotto.stackroute.io' +'/slot-management-service'+'/questions'
  baseurl="http://localhost:8080/questions"


  getAllCandidates(slot_date:any){
    return this.httpclient.get(this.baseurl+"/name/"+slot_date)
  }

  getCancelledSlots(slot_date:any){
    return this.httpclient.get(this.baseurl+"/cancelledSlots/"+slot_date);
  }
  
  getInterviewersAvailable(slot_date:any){
    return this.httpclient.get(this.baseurl+"/interviewer/"+slot_date)
  }
  getTotalSlots(slot_date:any){
    return this.httpclient.get(this.baseurl+"/count/"+slot_date);
  }
  getSlotUtilisation(slot_date:any){
     return this.httpclient.get(this.baseurl+"/getUtilization/"+slot_date).subscribe(response=>{
      console.log(response);
      this.values.push(Object.values(response));
        console.log(this.values)

    })
  }


  getutilizationofslots(slot_date:any)
  {

return this.httpclient.get(this.baseurl+"/getUtilization/"+slot_date);

  }
}


