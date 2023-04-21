import { TestBed } from '@angular/core/testing';

import { InterviewerAuthGuard } from './interviewer-auth.guard';

describe('InterviewerAuthGuard', () => {
  let guard: InterviewerAuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(InterviewerAuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
