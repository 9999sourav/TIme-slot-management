import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlotUtilizationComponent } from './slot-utilization.component';

describe('SlotUtilizationComponent', () => {
  let component: SlotUtilizationComponent;
  let fixture: ComponentFixture<SlotUtilizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SlotUtilizationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SlotUtilizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
