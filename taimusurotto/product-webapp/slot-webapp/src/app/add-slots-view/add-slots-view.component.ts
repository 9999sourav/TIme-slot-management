import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { FreezeTimeModalComponent } from '../freeze-time-modal/freeze-time-modal.component';
import { Slot } from '../model/slot';
import { SLOTS } from '../model/slotList';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-add-slots-view',
  templateUrl: './add-slots-view.component.html',
  styleUrls: ['./add-slots-view.component.css']
})
export class AddSlotsViewComponent {

  selectAll() {
    let date = this.date;
    this.creationSlots = [];
    for (let s of this.slots) {
      s.slot_date=date;
      this.creationSlots.push(s);
    }
  }

  allSlots = new Array();

  openSlots:any;


  deSelectAll() {
    this.creationSlots = [];
  }

  selected: Date | null | undefined
  slots = new Array();
  date: any;

  getDate(){
    this.date = this.selected!.getFullYear().toString();
    this.date = this.date.concat("-");
    if (this.selected!.getMonth() < 10) {
      this.date = this.date.concat("0" + (this.selected!.getMonth() + 1));
    }
    else {
      this.date = this.date.concat(this.selected!.getMonth() + 1);
    }
    this.date = this.date.concat("-");
    if (this.selected!.getDate() < 10) {
      this.date = this.date.concat("0" + this.selected!.getDate());
    }
    else
      this.date = this.date.concat(this.selected!.getDate())
  }

  showSlots() {
    this.getAllSlots()
    this.slots = this.allSlots;
    console.log(this.slots)
    //removing open slots
    let index = 0;
    console.log('data : ')
    console.log(this.openSlots)
    let indexes = new Array();
    for (let s of this.slots) {
      for (let o of this.openSlots)
        if (s.slot_start_Time == o.slot_start_Time) {
          console.log(o)
          indexes.push(index);
        }
      index++;
    }
    console.log(indexes)
    console.log('before removal of data :')
    console.log(this.slots)
    let removalIndex=0;
    if (indexes != null) {
      for (let i of indexes) {
        this.slots.splice(i-removalIndex, 1)
        removalIndex++;
      }
    }
    console.log('after removal of data : ')
    console.log(this.slots)
  }

  addToList(slot: any) {
    let count = 0;
    slot.slot_date = this.date;
    for (let s of this.creationSlots) {
      if (s.slot_start_Time == slot.slot_start_Time) {
        count++;
        Swal.fire('Slot Time Already Selected',
          '',
          'error')
      }
    }
    if (count == 0) {
      this.creationSlots.push(slot);
    }
  }

  openModal(){
    this.dialog.open(FreezeTimeModalComponent,{
      width:'600px'
    })
  }

  getAllSlots(){
    this.allSlots= []
    for(let slot of SLOTS){
      this.allSlots.push(slot)
    }
  }

  constructor(private adminService: AdminService, private dialog: MatDialog) { 
    this.minDate.setDate(this.minDate.getDate()+1);
  }


  minDate = new Date();

  createSlots() {
    console.log(this.creationSlots)
    let body = JSON.stringify(this.creationSlots);
    console.log(body);
    if (this.creationSlots.length == 0) {
      Swal.fire('No Slots Selected',
        '',
        'error')
    }
    else {
      this.adminService.addSlot(this.creationSlots).subscribe({
        next: data => {
          console.log(data)
          Swal.fire(
            'Slots Successfully Created',
            '',
            'success'
          ).then(() => window.location.reload())
        },
        error: e => {
          console.log(e)
          Swal.fire(
            'You seem to have run into some error',
            'Please try again',
            'error'
          )
        }
      })
    }
  }

  remove(slot: any) {
    this.creationSlots.forEach((s, index) => {
      if (s == slot) {
        this.creationSlots.splice(index, 1)
      }
    })
    console.log("after splice : ")
    console.log(this.creationSlots)
  }

  creationSlots = new Array<Slot>();

  onChange(){
    this.getDate();
    this.adminService.getAllSlotsFromSlotRepoByDate(this.date).subscribe({
      next:data=>{console.log(data)
      this.openSlots=data
      if(this.creationSlots.length!=0){
        Swal.fire({title : 'Selected Slots will be reset',
        showConfirmButton:true,
        showDenyButton:true,
        confirmButtonText:'Proceed',
        denyButtonText:'Cancel'}).then((result)=>{
          if(result.isConfirmed){
            this.creationSlots=[]
            this.showSlots();
          }
          else if(result.isDenied){
            console.log(this.selected);
          }
        })
      }
      else
      this.showSlots()
    }
    })
  }



}
