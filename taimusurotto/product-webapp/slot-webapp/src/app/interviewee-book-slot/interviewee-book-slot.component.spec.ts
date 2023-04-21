import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntervieweeBookSlotComponent } from './interviewee-book-slot.component';

describe('IntervieweeBookSlotComponent', () => {
  let component: IntervieweeBookSlotComponent;
  let fixture: ComponentFixture<IntervieweeBookSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntervieweeBookSlotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntervieweeBookSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
