import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelattionGraphComponent } from './cancelattion-graph.component';

describe('CancelattionGraphComponent', () => {
  let component: CancelattionGraphComponent;
  let fixture: ComponentFixture<CancelattionGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelattionGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancelattionGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
