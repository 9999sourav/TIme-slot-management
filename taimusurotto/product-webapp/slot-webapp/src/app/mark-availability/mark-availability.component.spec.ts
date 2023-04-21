import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarkAvailabilityComponent } from './mark-availability.component';

describe('MarkAvailabilityComponent', () => {
  let component: MarkAvailabilityComponent;
  let fixture: ComponentFixture<MarkAvailabilityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarkAvailabilityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarkAvailabilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
