import { Component } from '@angular/core';
import { Subject } from 'rxjs';
import Swal from 'sweetalert2';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-view-master-table',
  templateUrl: './view-master-table.component.html',
  styleUrls: ['./view-master-table.component.css']
})
export class ViewMasterTableComponent {
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any>=new Subject<any>();
  
  constructor(private adminService: AdminService){}
  ngOnInit(): void {
    this.adminService.getAllSlots().subscribe({
      next:data=>{console.log(data),this.allSlots=data, this.dtTrigger.next(null)}
    })
    this.dtOptions = {
      pagingType: 'full_numbers',
      // pageLength:5
    }
  }

  allSlots:any;

  cancelSlot(slot:any){
    console.log(slot)
    this.adminService.cancelInterview(slot.data_Id).subscribe({
      next:data=>{console.log(data)
        Swal.fire('Interview Cancelled','','success').then(()=>{window.location.reload()})
      },
      error:e=>{
        Swal.fire('Loos like you ran into some trouble','Sorry for the Inconvenience','error').then(()=>{window.location.reload()})
      }
    })
  }

}
