import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntervieweeDashboardComponent } from './interviewee-dashboard.component';

describe('IntervieweeDashboardComponent', () => {
  let component: IntervieweeDashboardComponent;
  let fixture: ComponentFixture<IntervieweeDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntervieweeDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntervieweeDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
