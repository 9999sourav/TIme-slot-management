import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntervieweeLoginComponent } from './interviewee-login.component';

describe('IntervieweeLoginComponent', () => {
  let component: IntervieweeLoginComponent;
  let fixture: ComponentFixture<IntervieweeLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntervieweeLoginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntervieweeLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
