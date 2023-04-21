import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { InterviewerDashboardComponent } from './interviewer-dashboard/interviewer-dashboard.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MarkAvailabilityComponent } from './mark-availability/mark-availability.component';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import {MatDialogModule} from '@angular/material/dialog';
import { MatTabsModule } from '@angular/material/tabs';
import { AddInterviewerComponent } from './add-interviewer/add-interviewer.component';
import { MatTableModule } from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ManageSlotsComponent } from './manage-slots/manage-slots.component';
import {DataTablesModule} from 'angular-datatables';
import { IntervieweeDashboardComponent } from './interviewee-dashboard/interviewee-dashboard.component';
import { IntervieweeBookSlotComponent } from './interviewee-book-slot/interviewee-book-slot.component';
import { ViewMasterTableComponent } from './view-master-table/view-master-table.component';
import {MatStepperModule} from '@angular/material/stepper';
import { DialogFormComponent } from './dialog-form/dialog-form.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { AdminDialogFormComponent } from './admin-dialog-form/admin-dialog-form.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AddSlotsViewComponent } from './add-slots-view/add-slots-view.component';
import {MatRadioModule} from '@angular/material/radio';
import { AnalyticalGraphsComponent } from './analytical-graphs/analytical-graphs.component';
import { CandidatesGraphComponent } from './candidates-graph/candidates-graph.component';
import { CancelattionGraphComponent } from './cancellation-graph/cancelattion-graph.component';
import { InterviewerHomeComponent } from './interviewer-home/interviewer-home.component';
import { InterviewerGraphComponent } from './interviewer-graph/interviewer-graph.component';
import { TotalSlotsGraphComponent } from './total-slots-graph/total-slots-graph.component';
import { FreezeTimeModalComponent } from './freeze-time-modal/freeze-time-modal.component';
import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import {
  GoogleLoginProvider
} from '@abacritt/angularx-social-login';
import { IntervieweeLoginComponent } from './interviewee-login/interviewee-login.component';
import { SlotUtilizationComponent } from './slot-utilization/slot-utilization.component';
import { AdminInterviewerLoginComponent } from './admin-interviewer-login/admin-interviewer-login.component';
import { InterviewerRegisterComponent } from './interviewer-register/interviewer-register.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AvatarPopUpComponent } from './avatar-pop-up/avatar-pop-up.component';
import {MatMenuModule} from '@angular/material/menu';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AdminDashboardComponent,
    InterviewerDashboardComponent,
    MarkAvailabilityComponent,
    AddInterviewerComponent,
  
    DialogFormComponent,
    IntervieweeBookSlotComponent,
    IntervieweeDashboardComponent,
    ManageSlotsComponent,
       ViewMasterTableComponent,
  ManageSlotsComponent,
  AddAdminComponent,
  AdminDialogFormComponent,
  AddSlotsViewComponent,
  AnalyticalGraphsComponent,
  CandidatesGraphComponent,

  CancelattionGraphComponent,
  InterviewerHomeComponent,
  InterviewerGraphComponent,
  TotalSlotsGraphComponent,
  SlotUtilizationComponent,
  InterviewerRegisterComponent,
      AdminInterviewerLoginComponent,
      IntervieweeLoginComponent,
  FreezeTimeModalComponent,
  HomeComponent,
  PageNotFoundComponent,
  ChangePasswordComponent,
  AvatarPopUpComponent,
  ],

  imports: [
    SocialLoginModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    HttpClientModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatChipsModule,
    MatTableModule,
    MatFormFieldModule,
    MatFormFieldModule,
    MatInputModule,
    DataTablesModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatTabsModule,
    MatRadioModule,
    MatInputModule,
    MatSelectModule,
    MatStepperModule,
    MatMenuModule
  ],
  providers: [
    {
    provide: 'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [
        {
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(
            '242992881690-fekakec9tnccl4q0pceo9dtk552e0cce.apps.googleusercontent.com'
          )
        }
      ],
      onError: (err) => {
        console.error(err);
      }
    } as SocialAuthServiceConfig,
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
