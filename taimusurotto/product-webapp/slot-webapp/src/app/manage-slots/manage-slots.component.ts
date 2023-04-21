import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-manage-slots',
  templateUrl: './manage-slots.component.html',
  styleUrls: ['./manage-slots.component.css']
})
export class ManageSlotsComponent {
  constructor(private adminService:AdminService, private router:Router) { }

  ngOnInit(): void {
  }
  date:any;
  selected:any;
  
  // filterDates = (d: Date ): boolean => {
  //   const day = (d || new Date()).getDay();
  //   return day !== 0 && day !== 6;
  // };
  filterDates = (d: Date ): boolean => {
    const day = (d || new Date()).getDay();
    return day !== 0 ;
  };
  // myDateFilter = (d: Date): boolean => {
  //   const day = d.getDay();
  //   return day !== 0 && day !== 6 ;
  // }
  myDateFilter = (d: Date): boolean => {
    const day = d.getDay();
    return day !== 0 ;
  }
  startAt = new Date();
  minDate = new Date();
  // maxDate = new Date('2023/02/28');
  slots:any;
  masterTable:any[]=[];

  slot:any;
 

  getAllSlots(){
    this.date=new DatePipe('en').transform(this.selected, 'yyyy-MM-dd');
    console.log(this.date);
    this.adminService.getAllSlotsForAParticularDay(this.date).subscribe({
      next:data=>{
        console.log(data)
        this.slots=data;
      },
      error:e=>{
        console.log(e)
      }
    })
  }
  // CancelInterview(data_Id:any){
  //   this.adminService.deleteSlot(data_Id).subscribe({
  //     next:data=>{
  //       console.log(data)
  //       Swal.fire(
  //         'Interview Cancelled',
  //         '',
  //         'success'
  //       ).then(()=>this.getAllSlots())
  //     },
  //     error:e=>console.log(e)
  //   });    
  // }



  // My Code


  addToList(slot:any){
    let count =0;
    for(let obj of this.deletionList){
      if(obj==slot){
        count++;
        Swal.fire(
          'Slot Already Chosen',
          'You have already picked this slot for deleting',
          'error'
        ).then(()=>{return})
      }
    }
    if(count==0)
    this.deletionList.push(slot);
  }

  deletionList = new Array();

  checkSlotsPresent(){
    if(this.slots!=undefined){
      if(this.slots[0]==null)
        return false
      else 
        return true
    }
    return true;
  }

  deleteSlot(slot:any){
Swal.fire({
  title: 'Are you sure you want to this slot',
  icon: 'question',
  iconHtml: '',
  confirmButtonText: 'Yes',
  cancelButtonText: 'No',
  showCancelButton: true,
  showCloseButton: true
}).then((result)=>{if(result.isConfirmed){
  this.adminService.deleteSlotFromSlotRepo(slot.slot_Id).subscribe({
    next:response=>{
      console.log(response),
      Swal.fire(
        'Successfully Deleted',
        '',
        'success'
      ).then(()=>{this.getAllSlots()});
    }
  });
}})
  }
 
}
