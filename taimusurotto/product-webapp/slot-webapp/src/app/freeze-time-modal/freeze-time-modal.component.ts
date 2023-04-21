import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { FreezeSlots } from '../model/freezeTime';
import { FreezeService } from '../services/freeze.service';

@Component({
  selector: 'app-freeze-time-modal',
  templateUrl: './freeze-time-modal.component.html',
  styleUrls: ['./freeze-time-modal.component.css']
})
export class FreezeTimeModalComponent {

  constructor(private freezeService: FreezeService ){}

  dateToday=new Date();
  tomorrow = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
  minDate = this.tomorrow;
  FreezingDate:any;
  FreezingTime:any;

  freezeTimeData = {
    "freeze_date":"",
    "freeze_time":0
  }

  freezingDate(event:any){
    console.log(event.value)
    this.FreezingDate=event.value;
  }


  freezingTime(event:any){
    console.log(event.value)
    this.FreezingTime=event.value;
  }

  // validate(){
  //   if(th)
  // }

  timeNow = new Date();

  slots=FreezeSlots;

  submit(){
    let date = this.FreezingDate!.getFullYear().toString();
    date = date.concat("-");
    if(this.FreezingDate!.getMonth()<10){
      date = date.concat("0"+(this.FreezingDate!.getMonth()+1));
    }
    else{
      date = date.concat(this.FreezingDate!.getMonth()+1);
    }
    date = date.concat("-");
    if(this.FreezingDate!.getDate()<10){
    date = date.concat("0"+this.FreezingDate!.getDate());
    }
    else
    date = date.concat(this.FreezingDate!.getDate())
    this.freezeTimeData.freeze_date=date;
    this.freezeTimeData.freeze_time= Number.parseInt(this.FreezingTime.substring(0,2))
    console.log(this.freezeTimeData);

    this.freezeService.saveFreezeTime(this.freezeTimeData).subscribe({
      next:response=>{
        Swal.fire('Freezing Time Set successfully',
        '',
        'success').then(()=>window.location.reload())
      },
      error:e=>{
        console.log(e)
        if(e.error=='Freeze Time has already been set for this date'){
          Swal.fire('Oops...',
          'Freeze Time has already been set for this date',
          'warning').then(()=>Swal.fire({
            title:'Do you wish to change the time?',
            showConfirmButton:true,
            showDenyButton:true,
            confirmButtonText:'Change Time',
            denyButtonText:'Cancel'
          }).then((response)=>{
              if(response.isConfirmed){
                 this.freezeService.updateFreezeTime(this.freezeTimeData).subscribe({
                  next:data=>{console.log(data)
                  Swal.fire('Freezing Time Set Successfully',
                  'Time has been Update',
                  'success').then(()=>{window.location.reload()})},
                  error:e=>{
                    Swal.fire('Error',
                    `e.error`,
                    'error').then(()=>window.location.reload()); 
                  }
                })
              }
              else if(response.isDenied){
                window.location.reload()
              }
          }))
        }
      }
    })    

  }

}
