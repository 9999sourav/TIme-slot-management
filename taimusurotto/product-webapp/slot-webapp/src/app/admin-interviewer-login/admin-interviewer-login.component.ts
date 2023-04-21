import { SocialAuthService } from '@abacritt/angularx-social-login';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { responseData } from '../model/ResponseData';
import { NavbarComponent } from '../navbar/navbar.component';
import { LoginService } from '../services/login.service';
import { SlotServiceService } from '../services/slot-service.service';

@Component({
  selector: 'app-admin-interviewer-login',
  templateUrl: './admin-interviewer-login.component.html',
  styleUrls: ['./admin-interviewer-login.component.css']
})
export class AdminInterviewerLoginComponent {

  user: any;
  loggedIn: any;


  // constructor(private authService: SocialAuthService, private httpClient: HttpClient, private service:SlotServiceService, private route:Router) { }
  // ngOnInit() {
  //   this.authService.authState.subscribe((user) => {
  //     this.user = user;
  //     this.loggedIn = (user != null);
  //     console.log(user);
  //     if(user!=null){
  //       localStorage.setItem('Role',"Interviewee")
  //      this.service.addInterviewee(this.user).subscribe(response=>{
  //       this.service.user=response
  //       console.log(response)
  //       this.router.navigate(["/interviewee"])
  //      });
  
  //     }
  //   });
  // }

  login(){}


  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router,
    private authService: SocialAuthService, private httpClient: HttpClient, private service:SlotServiceService,
    private navbar : NavbarComponent) { }
  interviewersdata: any
  registerForm: FormGroup=this.fb.group({

    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],

  })


  loginResponse:any;

  loginData:any={
    "email":"",
    "password":""
  }



  onSubmit() {
    this.loginData.email=this.registerForm.value.email
    this.loginData.password=this.registerForm.value.password
    console.log(this.loginData)
    this.loginService.login(this.loginData).subscribe({
      next:response=>{
        this.loginResponse=response;
        localStorage.setItem("Name",this.loginResponse.Name)
        localStorage.setItem("Role",this.loginResponse.Role)
        localStorage.setItem("Id",this.loginResponse.Id)
        localStorage.setItem("Email",this.loginResponse.Email)
        Swal.fire(
          `Welcome ${this.loginResponse.Name}`,
          "You're Successgully logged in",
          'success'
        ).then(()=>{
          let email=localStorage.getItem("Email")
            email = email!.charAt(0).toUpperCase() + email!.slice(1);
            let str = email.split('@')
            email = str[0]
          
          this.navbar.Email= email;
          console.log(localStorage.getItem("Role"))
          if(localStorage.getItem("Role")=='Admin'){
            this.router.navigate(['/adminDashboard']) 
          }
          else if(localStorage.getItem("Role")=='Interviewer'){
            this.router.navigate(['interviewer'])
          }
        })
      },
      error:e=>{
        console.log(e)
        if(e.status==409)
        Swal.fire(
          `${e.error}`,
          ``,
          'error'
        )
        else{
          Swal.fire('Looks Like we are facing some issues at the moment','Please try again later','error');
        }
      }
    })
  }

  hide = true;
}
