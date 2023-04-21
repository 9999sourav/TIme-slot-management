import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class LoginAuthGuard implements CanActivate {
  constructor(private router:Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(localStorage.getItem('Role')==null)
      return true;
      else{
        Swal.fire('No Access',
        'You cannot access login page while logged in',
        'warning').then(()=>{
        if(localStorage.getItem('Role')=='Admin')
        this.router.navigate(['adminDashboard'])
        else if(localStorage.getItem('Role')=='Interviewer')
        this.router.navigate(['interviewer'])
        else
        this.router.navigate(['interviewee'])
        })
        return false;
      }
  }
  
}
