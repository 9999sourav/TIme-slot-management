import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IntervieweeBookSlotComponent } from './interviewee-book-slot/interviewee-book-slot.component';
import { IntervieweeDashboardComponent } from './interviewee-dashboard/interviewee-dashboard.component';
import { AddInterviewerComponent } from './add-interviewer/add-interviewer.component';
import { InterviewerDashboardComponent } from './interviewer-dashboard/interviewer-dashboard.component';
import { ManageSlotsComponent } from './manage-slots/manage-slots.component';
import { MarkAvailabilityComponent } from './mark-availability/mark-availability.component';
import { ViewMasterTableComponent } from './view-master-table/view-master-table.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AddSlotsViewComponent } from './add-slots-view/add-slots-view.component';
import { AnalyticalGraphsComponent } from './analytical-graphs/analytical-graphs.component';
import { CandidatesGraphComponent } from './candidates-graph/candidates-graph.component';
import { InterviewerHomeComponent } from './interviewer-home/interviewer-home.component';
import { CancelattionGraphComponent } from './cancellation-graph/cancelattion-graph.component';
import { InterviewerGraphComponent } from './interviewer-graph/interviewer-graph.component';
import { TotalSlotsGraphComponent } from './total-slots-graph/total-slots-graph.component';


import { SlotUtilizationComponent } from './slot-utilization/slot-utilization.component';
import { IntervieweeLoginComponent } from './interviewee-login/interviewee-login.component';
import { AdminInterviewerLoginComponent } from './admin-interviewer-login/admin-interviewer-login.component';
import { HomeComponent } from './home/home.component';
import { AdminAuthGuard } from './services/admin-auth.guard';
import { InterviewerAuthGuard } from './services/interviewer-auth.guard';
import { IntervieweeAuthGuard } from './services/interviewee-auth.guard';
import { LoginAuthGuard } from './services/login-auth.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

const routes: Routes = [
  {"path":"",redirectTo:"home",pathMatch:"full"},
  {"path":"home",component:HomeComponent},
  {"path":"login",component:AdminInterviewerLoginComponent,canActivate:[LoginAuthGuard]},

  {"path":"interviewer",component:InterviewerHomeComponent,canActivate:[InterviewerAuthGuard]},
  {"path":"showSlots",component:InterviewerDashboardComponent,canActivate:[InterviewerAuthGuard]},
  {"path":"markAvailability",component:MarkAvailabilityComponent,canActivate:[InterviewerAuthGuard]},

  {"path":"interviewee",component:IntervieweeDashboardComponent,canActivate:[IntervieweeAuthGuard] },
  {"path":"bookSlot",component:IntervieweeBookSlotComponent,canActivate:[IntervieweeAuthGuard] },

  {"path":"addinterviewer",component:AddInterviewerComponent,canActivate:[AdminAuthGuard]},
  {"path":"manageSlots",component:ManageSlotsComponent,canActivate:[AdminAuthGuard]},
  {"path":"addInterviewer",component:AddInterviewerComponent,canActivate:[AdminAuthGuard]},
  {"path":"viewAllSlots",component:ViewMasterTableComponent,canActivate:[AdminAuthGuard]},
  {"path":"addAdmin",component:AddAdminComponent,canActivate:[AdminAuthGuard]},
  {"path":"slotview",component:AddSlotsViewComponent,canActivate:[AdminAuthGuard]},
  {"path":"adminDashboard",component:AdminDashboardComponent,canActivate:[AdminAuthGuard]},
  {"path":"analyticsDashboard",component:AnalyticalGraphsComponent,canActivate:[AdminAuthGuard]},

  {"path":"candidateGraph",component:CandidatesGraphComponent,canActivate:[AdminAuthGuard]},
  {"path":"cancellationGraph",component:CancelattionGraphComponent,canActivate:[AdminAuthGuard]},
  {"path":"interviewerGraph",component:InterviewerGraphComponent,canActivate:[AdminAuthGuard]},
  {"path":"slotsGraph",component:TotalSlotsGraphComponent,canActivate:[AdminAuthGuard]},
  {"path":"slotUtilization",component:SlotUtilizationComponent,canActivate:[AdminAuthGuard]},

  {"path":"changePassword",component:ChangePasswordComponent},


  {"path":"candidateLogin",component:IntervieweeLoginComponent,canActivate:[LoginAuthGuard]},
  {"path":"**",component:PageNotFoundComponent}
];

@NgModule({
imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
