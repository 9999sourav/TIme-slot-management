import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ADTSettings } from 'angular-datatables/src/models/settings';
import { Subject } from 'rxjs';
import Swal from 'sweetalert2';
import { InterviewerService } from '../services/interviewer.service';
@Component({
  selector: 'app-interviewer-dashboard',
  templateUrl: './interviewer-dashboard.component.html',
  styleUrls: ['./interviewer-dashboard.component.css']
})
export class InterviewerDashboardComponent {
  interviewerID=localStorage.getItem("Id");
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any>=new Subject<any>();
  constructor(private is : InterviewerService, private router:Router) {
    this.is.getAllSlotsForInterviewer(this.interviewerID).subscribe(data=>{
      this.bookedSlots=[];
      console.log(data);
      this.bookedSlots=data,this.dtTrigger.next(null);});
  }
  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers'
    }
  }
  bookedSlots:any;

  markAttendance(slot: any) {
    Swal.fire({
      title: 'Mark Attendance',
      showCancelButton: true,
      showDenyButton: true,
      confirmButtonText: 'Mark Present',
      denyButtonText: "Mark Absent",
      showLoaderOnConfirm: true,
      showLoaderOnDeny: true,
      preConfirm: () => {
        {
          // change url
          // to mark present
          return fetch('https://taimusurotto.stackroute.io' +'/slot-management-service'+'/interviewer/addremarks/'+slot.data_Id,{
            method:"POST",
            headers: {
              'Content-Type': 'application/json'
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body:JSON.stringify('Present')
          })
            .then(response => {
              console.log(response)
              if (!response.ok) {
                throw new Error(response.statusText)
              }
              return response.json()
            })
            .catch(error => {
              Swal.showValidationMessage(
                `Request failed: ${error}`
              )
            })
        }
      },
      preDeny: () => {
        //change url
        // to mark absent
        return fetch('https://taimusurotto.stackroute.io' +'/slot-management-service'+'/interviewer/addremakrs/'+slot.data_Id,{
          method:"POST",
          headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
          },
          body:JSON.stringify('Absent')
        }) // change api
          .then(response => {
            if (!response.ok) {
              throw new Error(response.statusText)
            }
            return response.json()
          })
          .catch(error => {
            Swal.showValidationMessage(
              `Request failed: ${error}`
            )
          })
      }
      ,
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          `Marked Present`,
          '',
          'success'
        ).then(()=>window.location.reload())
      }
      if (result.isDenied) {
        Swal.fire(
          `Marked Absent`,
          '',
          'success'
        ).then(()=>window.location.reload())
      }
    })
  }


  cancelSlot(slot: any) {
    Swal.fire({
      title: 'Reason To cancel Slot',
      input: 'textarea',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Submit',
      showLoaderOnConfirm: true,
      preConfirm: (login) => {
        if (login == '') {
          return Swal.fire(
            'Reason cannot be empty',
            'Please provide a reason to cancel slot',
            'error'
          )
        }
        console.log(slot.slot_id.slot_Id)
        return fetch('https://taimusurotto.stackroute.io' +'/slot-management-service'+'/interviewer/cancelSlot/'+this.interviewerID+"/"+slot.slot_id.slot_Id,{
          method:'POST',
          headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
          },
          body:JSON.stringify(login)
        })
          .then(response => {
            return response.json();
          })
          .catch(error => {
            Swal.showValidationMessage(
              `Request failed: ${error}`
            )
            console.log(error)
          })
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      console.log(result)
      if (result.value!=null) {
          Swal.fire(
            'Successfully Cancelled',
            '',
            'success'
          ).then(()=>this.is.getAllSlotsForInterviewer(this.interviewerID).subscribe(data=>this.bookedSlots=data))
        }
      else if(result.isDismissed || result.isDenied){
        return;
      }
      else{
        Swal.fire(
          'Error',
          'Some Unexpected Error occured',
          'error'
        )
      }
      }
    )
  }


  route(link:any){
    this.router.navigate([link]);
  }


}
