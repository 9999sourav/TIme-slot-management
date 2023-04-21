import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {

  constructor(private fb : FormBuilder, private loginService : LoginService){}

  registerForm = this.fb.group({    
    password: ['', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]],
    cpassword: ['', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]],
  })

  onSubmit(){
    if(this.registerForm.value.password!=this.registerForm.value.cpassword){
      Swal.fire('Password And Confirm Password must match','','error')
      return;
    }
    else{
      console.log(this.registerForm.value)
      this.loginService.resetPassword(this.registerForm.value.password).subscribe({
        next:data=>{
          console.log(data)
          Swal.fire('Password Changed Successfully','','success').then(()=>{window.location.reload()})
        },
        error:e=>{
          console.log(e)
          Swal.fire('Sorry','It Seems Like you ran into some trouble','error').then(()=>window.location.reload())
        }
      })
    }

  }


}
