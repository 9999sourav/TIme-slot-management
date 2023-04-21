import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'app-avatar-pop-up',
  templateUrl: './avatar-pop-up.component.html',
  styleUrls: ['./avatar-pop-up.component.css']
})
export class AvatarPopUpComponent {

  constructor(private router:Router, private dialog: MatDialog){}

  logout(){
    localStorage.clear()
    Swal.fire('Visit Again','Logged Out Successfully','success')
    this.router.navigate(['home'])
  }

  changePassword(){
    this.dialog.open(ChangePasswordComponent,{width:'600px'})
  }


  
}
