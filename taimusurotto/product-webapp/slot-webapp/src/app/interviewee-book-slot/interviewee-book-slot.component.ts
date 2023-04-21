import { Component, OnInit, ViewChild } from '@angular/core';
import {
  MatCalendar,
  MatCalendarCellCssClasses,
} from '@angular/material/datepicker';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Slot } from '../model/slot';
import { SlotServiceService } from '../services/slot-service.service';
@Component({
  selector: 'app-interviewee-book-slot',
  templateUrl: './interviewee-book-slot.component.html',
  styleUrls: ['./interviewee-book-slot.component.css'],
})
export class IntervieweeBookSlotComponent implements OnInit {
  selected!: Date | null;
  slot: any;
  slots: Slot[] = [];
  respdata: any;
  datesToBeHighlighted: Date[] = [];
  showSlots: Slot[] = [];
  // startAt = new Date();
  minDate = new Date();
  // maxDate = new Date(new Date().setMonth(new Date().getMonth() + 1));
  // maxDate = new Date('2023/02/28');

  ngOnInit() {
    this.slotService.getslots().subscribe((response: any) => {
      this.respdata = response;
      for (let slot of this.respdata) {
        let slotDate = slot.slot_id.slot_date;
        let today = new Date();
        if (!this.datesToBeHighlighted.includes(slotDate) && today<=new Date(slotDate))
          this.datesToBeHighlighted.push(slotDate);
      }
      console.log(this.datesToBeHighlighted.length);
      this.someEvent()
    });
  }
  @ViewChild(MatCalendar) calendar: MatCalendar<Date> | undefined;

  someEvent() {
    this.calendar!.updateTodaysDate();
  }
  constructor(
    private slotService: SlotServiceService,
    private router: Router
  ) {this.minDate.setDate(this.minDate.getDate()+1);}

  // bookSlot() {
  //   if(this.slot===undefined){
  //     alert("choose a slot")
  //   }
  //   this.slotService.bookSlot(this.slot.slot_Id).subscribe({
  //     next: (data) => {
  //       console.log(data);
  //       Swal.fire(
  //         'Slot Booked Successfulyl',
  //         `You have booked slot for ${this.selected} on ${this.slot.slot_start_Time} to ${this.slot.slot_end_Time}`,
  //         'success'
  //       ).then(() => this.router.navigate(['/interviewee']));
  //     },
  //   });
  // }
  changeSlot(slot: any) {
    this.slot = slot;
  }
  date:any;
  myDateFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  };
  dateChanged(event: any) {
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
    this.slots = [];
    this.slotService.getslotsByDate(this.date).subscribe((response) => {
      this.respdata = response;
      console.log(response);

      for (let slot of this.respdata) {
        let check = false;
        for (let slotInSlots of this.slots) {
          if (slotInSlots.slot_Id === slot.slot_id.slot_Id) {
            check = true;
          }
        }
        if (!check) {
          this.slots.push(slot.slot_id);
          console.log(this.slots);
        }
      }
    });
  }
  dateClass() {
    return (date: Date): MatCalendarCellCssClasses => {
      let cssClass = '';
      console.log(this.datesToBeHighlighted.length);
      for (let data of this.datesToBeHighlighted) {
        console.log('in');
        let dataDate = new Date(data);
        console.log(dataDate);
        if (date.getDate() === dataDate.getDate()) {
          cssClass = 'special-date';
        }
      }
      return cssClass;
    };
  }

  bookSlot() {
    Swal.fire({
      title: 'Book Slot from '+this.slot.slot_start_Time +" to "+this.slot.slot_end_Time,
      showCancelButton: true,
      confirmButtonText: 'Book',
      showLoaderOnConfirm: true,
      preConfirm: () => {
        {
          // change url
          // to mark present
          return fetch("http://localhost:8080"+'/interviewee/book/'+this.slot.slot_Id,{
            method:"POST",
            headers: {
              'Content-Type': 'application/json'
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body:localStorage.getItem('Id')//interviewee_Id
          })
            .then(response => {
              console.log(response)
              if (!response.ok) {
                throw new Error(response.statusText)
              }
              return response.json()
            })
        }
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          `Booked Slot`,
          '',
          'success'
        ).then(()=>this.router.navigate(['/interviewee']))
      }
    })
  }

}
