import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { InterviewerService } from '../services/interviewer.service';

@Component({
  selector: 'app-mark-availability',
  templateUrl: './mark-availability.component.html',
  styleUrls: ['./mark-availability.component.css']
})
export class MarkAvailabilityComponent {

  constructor(private is:InterviewerService){
    this.minDate.setDate(this.minDate.getDate()+1);
  }

  slots:any;
  
  selected!: Date | null;

  date:any;

  openSlots = new Array();

  checkSlotsPresent(){
    if(this.slots!=undefined){
      if(this.slots[0]==null)
        return false
      else 
        return true
    }
    return true;
  }

  minDate = new Date();
  
  SelectAll(){
    this.openSlots=[];
    for(let s of this.slots){
       this.openSlots.push(s);
    }
  }

  deSelectAll(){
    this.openSlots=[]
  }

  getSlots(){
    this.date = this.selected!.getFullYear().toString();
    this.date = this.date.concat("-");
    if(this.selected!.getMonth()<10){
      this.date = this.date.concat("0"+(this.selected!.getMonth()+1));
    }
    else{
      this.date = this.date.concat(this.selected!.getMonth()+1);
    }
    this.date = this.date.concat("-");
    if(this.selected!.getDate()<10){
    this.date = this.date.concat("0"+this.selected!.getDate());
    }
    else
    this.date = this.date.concat(this.selected!.getDate())
    this.is.getSlotsForDate(this.date).subscribe(data=>this.slots=data);
  }

  remove(slot:any){
    this.openSlots.forEach((s,index)=>{
      if(s==slot){
       this.openSlots.splice(index,1)
      }
    })
    console.log("after splice : ")
    console.log(this.openSlots)
  }

  markAvailable(slot:any){
    for(let s of this.openSlots){
      if(s==slot)  //alert already chosen
        return;
    }
    this.openSlots.push(slot)
  }

  openChosenSlots(){
    const slot_ids = new Array();
    for(let slot of this.openSlots){
      slot_ids.push(slot.slot_Id)
    }
    console.log(slot_ids)
    this.is.markAviailability(slot_ids).subscribe({
      next:data=>{
        console.log(data)
      this.openSlots=[],
      this.selected=null,
      this.slots=null,
      Swal.fire(
        'Slots Opened',
        'Slots marked as available by you',
        'success'
      )
    },
      error:e=>console.log(e)
    })
  }

}
