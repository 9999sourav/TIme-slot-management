import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SlotServiceService {
  user:any
  // baseUrl='https://taimusurotto.stackroute.io' +'/slot-management-service'
  // url = this.baseUrl + '/interviewee/allSlots';
  // bookSlotUrl = this.baseUrl + '/interviewee/book';
  // bookedSlotByInterviewee = this.baseUrl + '/interviewee/boookedslot';
  // cancelSlotUrl = this.baseUrl + '/interviewee/delete';
  url="http://localhost:8080/interviewee/allSlots";
  bookSlotUrl="http://localhost:8080/interviewee/book"
  bookedSlotByInterviewee= "http://localhost:8080/interviewee/boookedslot"
  cancelSlotUrl="http://localhost:8080/interviewee/delete"
  intervieweeID=localStorage.getItem("Id")

   getslots(){
   return this.http.get<Array<any>>(this.url);
  }
  getslotsByDate(date:string):Observable<Array<any>>{
    return this.http.get<Array<any>>(this.url+`/${date}`);
   }
   getBookedSlot(intervieweeID:any){
    return this.http.get(this.bookedSlotByInterviewee+`/${intervieweeID}`)
   }
   cancelSlot(slot_id:number){
    return this.http.post(this.cancelSlotUrl+`/${slot_id}`,this.intervieweeID)

   }
   addInterviewee(user:any){
    // return this.http.post( this.baseUrl+"/interviewee" ,user)
    return this.http.post("http://localhost:8080/interviewee",user)
   }

  constructor(private http:HttpClient) { }
}
