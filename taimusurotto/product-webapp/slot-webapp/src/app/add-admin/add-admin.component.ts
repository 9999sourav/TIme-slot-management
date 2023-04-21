import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { AdminDialogFormComponent } from '../admin-dialog-form/admin-dialog-form.component';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css']
})
export class AddAdminComponent implements OnInit {



  admindata: any


  submitted = false;

  constructor(private adminService: AdminService, private fb: FormBuilder, private dialog: MatDialog) { }
  displayedColumns: string[] = ['position', 'name', 'Action'];

  ngOnInit(): void {
    this.getallAdminDetails();
  }


  getallAdminDetails() {
    this.adminService.getAllAdmins().subscribe(
      response => {
        console.log(response)
        this.admindata = response;

      }

    )

  }


  deleteAdmin(adminId: any) {
    this.adminService.deleteAdmin(adminId).subscribe({
      next:data=>{
        console.log(data);
        Swal.fire('Deleted', 'Admin has been deleted', 'success').then(()=>this.getallAdminDetails());
      },
      error:e=>{
        console.log(e)
        Swal.fire('You seem to have run into some error','Please Try again later','error');
      }
    })
  }



  opendialog() {

    this.dialog.open(AdminDialogFormComponent, {

      width: '600px',

    })

  }


}



