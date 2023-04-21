import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class IntervieweeAuthGuard implements CanActivate {
  constructor(private router:Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(localStorage.getItem('Role')==='Interviewee')
      return true;
      else{this.router.navigate(['login'])
        // Swal.fire('No Access',
        // 'Only candidates can view this page after logging in',
        // 'warning').then(()=>{
        //   this.router.navigate(['login'])
        // })
        return false;
      }
  }
  
}
