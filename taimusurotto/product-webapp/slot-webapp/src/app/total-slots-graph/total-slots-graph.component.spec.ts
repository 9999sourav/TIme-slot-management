import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalSlotsGraphComponent } from './total-slots-graph.component';

describe('TotalSlotsGraphComponent', () => {
  let component: TotalSlotsGraphComponent;
  let fixture: ComponentFixture<TotalSlotsGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TotalSlotsGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalSlotsGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
