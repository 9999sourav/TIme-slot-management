import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  constructor(private router:Router, private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  alertURL(){
    console.log(this.router.url)
  }

  changePasswordModal(){
    this.dialog.open(ChangePasswordComponent,{width:'600px'})
  }

}
