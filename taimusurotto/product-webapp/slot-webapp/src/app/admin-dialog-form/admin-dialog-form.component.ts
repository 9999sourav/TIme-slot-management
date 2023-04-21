import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-admin-dialog-form',
  templateUrl: './admin-dialog-form.component.html',
  styleUrls: ['./admin-dialog-form.component.css']
})
export class AdminDialogFormComponent implements OnInit {


  constructor(private fb: FormBuilder, private adminService: AdminService) { }
  interviewersdata: any
  registerForm!: FormGroup
  ngOnInit(): void {



    this.registerForm = this.fb.group({

      userName: ['', [Validators.required, Validators.minLength(4)]],
      email:['',[Validators.required,Validators.email]],

    })



  }




  onSubmit() {
    console.log(this.registerForm.value);
    Swal.fire({
      title:'Please wait while the admin is being added',
      timer:30000,
      didOpen:()=>{Swal.showLoading()}
    })
    this.adminService.addAdmin(this.registerForm.value).subscribe(
      {
        next:data=>{
          console.log(data)
        Swal.fire('Admin Added Successfully','', 'success').then(()=>window.location.reload())
          
        },
        error:e=>{console.log(e),
        Swal.fire('Error',
        e.error,
        'error').then(()=>window.location.reload())}
      }

    )

  }



}
