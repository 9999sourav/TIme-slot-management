import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminInterviewerLoginComponent } from './admin-interviewer-login.component';

describe('AdminInterviewerLoginComponent', () => {
  let component: AdminInterviewerLoginComponent;
  let fixture: ComponentFixture<AdminInterviewerLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminInterviewerLoginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminInterviewerLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
