import { SocialAuthService } from '@abacritt/angularx-social-login';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { SlotServiceService } from '../services/slot-service.service';

@Component({
  selector: 'app-interviewee-login',
  templateUrl: './interviewee-login.component.html',
  styleUrls: ['./interviewee-login.component.css']
})
export class IntervieweeLoginComponent {
  user: any;
  loggedIn: any;

  loginResponse:any;


  constructor(private authService: SocialAuthService, private httpClient: HttpClient, private service:SlotServiceService, private route:Router, private navbar:NavbarComponent) { }
  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
      console.log(user);
      if(user!=null){
       this.service.addInterviewee(this.user).subscribe(response=>{
        this.loginResponse=response;
        localStorage.setItem("Id",this.loginResponse.intervieweeID)
        localStorage.setItem("Name",this.loginResponse.firstName+" "+this.loginResponse.lastName)
        localStorage.setItem("Email",this.loginResponse.email)
        localStorage.setItem("Role",'Interviewee')
        this.service.user=response
        let email=localStorage.getItem("Email")
        email = email!.charAt(0).toUpperCase() + email!.slice(1);
        let str = email.split('@')
        email = str[0]    
        this.navbar.Email= email;
        this.route.navigate(["/interviewee"])
       });
  
      }
    });
  }

  login(){}

}
