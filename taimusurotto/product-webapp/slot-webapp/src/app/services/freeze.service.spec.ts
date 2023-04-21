import { TestBed } from '@angular/core/testing';

import { FreezeService } from './freeze.service';

describe('FreezeService', () => {
  let service: FreezeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FreezeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
