import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalyticalGraphsComponent } from './analytical-graphs.component';

describe('AnalyticalGraphsComponent', () => {
  let component: AnalyticalGraphsComponent;
  let fixture: ComponentFixture<AnalyticalGraphsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnalyticalGraphsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalyticalGraphsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
