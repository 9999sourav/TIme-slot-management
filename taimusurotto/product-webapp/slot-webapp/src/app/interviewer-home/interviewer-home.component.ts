import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'app-interviewer-home',
  templateUrl: './interviewer-home.component.html',
  styleUrls: ['./interviewer-home.component.css']
})
export class InterviewerHomeComponent {
  constructor(private dialog: MatDialog){}


  changePasswordModal(){
    this.dialog.open(ChangePasswordComponent,{width:'600px'})
  }

}
