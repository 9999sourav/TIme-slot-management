import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { data } from 'jquery';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { AvatarPopUpComponent } from '../avatar-pop-up/avatar-pop-up.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
    
    url:any;


  constructor(private breakpointObserver: BreakpointObserver, private router: Router, private dialog:MatDialog) {
    if(this.Email!=null){
      this.Email =this.Email.charAt(0).toUpperCase() + this.Email.slice(1);
      let str = this.Email.split('@')
      this.Email = str[0]
    }
  }


  getUrl(){
    this.url=window.location.href;
    let datas=this.url.split("/");
    let currentPage = datas[data.length]
    return currentPage;
  }

  changePasswordModal(){
    this.dialog.open(ChangePasswordComponent,{width:'600px'})
  }

  popup(){
    this.dialog.open(AvatarPopUpComponent,{width:'400px'})
  }

  checkRole(){
    return localStorage.getItem("Role")
  }
  

  checkLogin(){
    if(localStorage.getItem("Role")==null)
    return false;
    else
    return true;
  }

  logout(){
    localStorage.clear()
    Swal.fire('Visit Again','Logged Out Successfully','success')
    this.router.navigate(['home'])
  }

  Email = localStorage.getItem('Email')

}
