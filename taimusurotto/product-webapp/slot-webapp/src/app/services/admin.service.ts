import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(private httpclient:HttpClient) { }
  // baseurl='https://taimusurotto.stackroute.io' +'/slot-management-service'+'/admin'
  baseurl="http://localhost:8080/admin"


  cancelInterview(data_id:any){
    return this.httpclient.delete(this.baseurl+"/cancelSlot/"+data_id);
  }

  deleteSlotFromSlotRepo(slot_id:number){
   return this.httpclient.delete(this.baseurl+"/deleteSlot/"+slot_id);
  }

  getAllSlotsFromSlotRepoByDate(date:any){
    return this.httpclient.get(this.baseurl+"/getSlots/"+date);
  }

  getAllInterviewers()
  {
    return this.httpclient.get(this.baseurl+"/getAllInterviewers")
  }

  deleteInterviewer(interviewerId:any)
  {
    return this.httpclient.delete(this.baseurl+"/deleteInterviewer/"+interviewerId)
  }

  addInterviewer(interviewerobj:any)
  {
    return this.httpclient.post(this.baseurl+"/addInterviewer",interviewerobj)
  }

  getAllSlotsForAParticularDay(date:any){
    return this.httpclient.get(this.baseurl+"/"+date);
 }

 deleteSlot(dataId:any){
  return this.httpclient.delete(this.baseurl+"/delete/"+dataId);
 }

 getAllSlots(){
  return this.httpclient.get(this.baseurl+"/getAllSlots");
 }

 addAdmin(adminobj:any)
 {
  return this.httpclient.post(this.baseurl+"/addAdmin",adminobj)
 }

 getAllAdmins()
 {
   return this.httpclient.get(this.baseurl+"/getAllAdminDetails")
 }

 deleteAdmin(AdminId:any)
 {
   return this.httpclient.delete(this.baseurl+"/deleteAdmin/"+AdminId)
 }


 addSlot(slotList:any)
 {
  return this.httpclient.post(this.baseurl+"/createSlots",slotList)
 }
 


}
