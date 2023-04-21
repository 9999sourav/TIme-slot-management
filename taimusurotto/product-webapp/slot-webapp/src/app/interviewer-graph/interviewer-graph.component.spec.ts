import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterviewerGraphComponent } from './interviewer-graph.component';

describe('InterviewerGraphComponent', () => {
  let component: InterviewerGraphComponent;
  let fixture: ComponentFixture<InterviewerGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InterviewerGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InterviewerGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
