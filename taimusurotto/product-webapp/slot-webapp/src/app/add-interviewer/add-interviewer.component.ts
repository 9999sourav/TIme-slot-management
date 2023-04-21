import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { map, Observable, shareReplay } from 'rxjs';
import Swal from 'sweetalert2';
import { DialogFormComponent } from '../dialog-form/dialog-form.component';



import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-add-interviewer',
  templateUrl: './add-interviewer.component.html',
  styleUrls: ['./add-interviewer.component.css']
})
export class AddInterviewerComponent implements OnInit {

  interviewersdata: any



  submitted = false;

  constructor(private adminService: AdminService, private fb: FormBuilder, private dialog: MatDialog) { }
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol', 'ConactNumber', 'Action'];

  ngOnInit(): void {
    this.getallinterviewerdetails();
  }


  getallinterviewerdetails() {
    this.adminService.getAllInterviewers().subscribe(
      response => {
        console.log(response)
        this.interviewersdata = response;
      }
    )
  }


  deleteInterviewer(interviewerId: any) {
    this.adminService.deleteInterviewer(interviewerId).subscribe(response => {
      this.getallinterviewerdetails();
      console.log(response)
    })

    Swal.fire(" Deleted", "Interviewer has been deleted", "success").then(()=> window.location.reload())
   
  }

  opendialog() {
    this.dialog.open(DialogFormComponent, {
      width: '600px',
    })
  }

}





