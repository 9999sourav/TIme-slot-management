import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SlotServiceService } from '../services/slot-service.service';


@Component({
  selector: 'app-interviewee-dashboard',
  templateUrl: './interviewee-dashboard.component.html',
  styleUrls: ['./interviewee-dashboard.component.css']
})
export class IntervieweeDashboardComponent implements OnInit {
  disabledCancel=true
  disabledBook=true
  bookedSlot:any

  constructor(private slotService:SlotServiceService, private router:Router,private http:HttpClient)  { }

  interviewerID = localStorage.getItem("Id")

  ngOnInit(): void {
    this.empty=false;
    this.slotService.getBookedSlot(this.interviewerID).subscribe(response=>{
      console.log(response);
      if(response===null){
        this.disabledBook=false
       
        console.log(this.disabledCancel)
      
      }
      else{
        this.disabledCancel=false
        this.bookedSlot=response

      }
      console.log(this.bookedSlot);
    })

  }

  empty = false;

  cancel(){
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
          this.empty = true;
          return Swal.fire(
            'Reason cannot be empty',
            'Please provide a reason to cancel slot',
            'error'
          )
        }
        return fetch('https://taimusurotto.stackroute.io' +'/slot-management-service'+'/interviewee/delete/'+this.bookedSlot.slot_id.slot_Id+"/"+this.interviewerID,{
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
      if(this.empty){
        return;
      }
      console.log(result)
      if (result.value!=null) {
          Swal.fire(
            'Successfully Cancelled',
            '',
            'success'
          ).then(()=>window.location.reload())
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

  
  book(){
    this.router.navigate(['/', 'bookSlot'])
    
  }

}


// response=>{

  // alert(`You have cancelled the slot for ${this.bookedSlot.slot_id.slot_date} from ${this.bookedSlot.slot_id.slot_start_Time} to ${this.bookedSlot.slot_id.slot_end_Time}`)
  // this.router.navigate(['/', 'interviewee'])
// }