import { TestBed } from '@angular/core/testing';

import { IntervieweeAuthGuard } from './interviewee-auth.guard';

describe('IntervieweeAuthGuard', () => {
  let guard: IntervieweeAuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IntervieweeAuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
