import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-dialog-form',
  templateUrl: './dialog-form.component.html',
  styleUrls: ['./dialog-form.component.css']
})
export class DialogFormComponent implements OnInit {


  constructor(private fb:FormBuilder,private adminService:AdminService, private router:Router){}
interviewersdata:any
  registerForm! : FormGroup
  ngOnInit(): void {
    this.registerForm = this.fb.group({
      firstName:['',[Validators.required,Validators.minLength(4)]],
      lastName:['',[Validators.required,Validators.minLength(4)]],
      email:['',[Validators.required,Validators.email]],
      phoneNumber:['',[Validators.pattern(/^[789]\d{9,9}$/)]]
    })
  }
  
  onSubmit()
  {
    console.log(this.registerForm.value);
    Swal.fire({
      title:'Please wait while the interviewer is being added',
      timer:30000,
      didOpen:()=>{Swal.showLoading()}
    })
    this.adminService.addInterviewer(this.registerForm.value).subscribe(
      {
        next:data=>{
          Swal.fire('Congrats','You Submitted Sucessfully','success').then(()=>{
            window.location.reload();
          })
        },
        error:e=>{console.log(e),
        Swal.fire('Seems like you ran into some trouble',
        e.error,
        'error').then(()=>window.location.reload())}
      }
    )
    
  
  }






  
}
