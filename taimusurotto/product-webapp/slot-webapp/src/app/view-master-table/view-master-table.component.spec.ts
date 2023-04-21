import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMasterTableComponent } from './view-master-table.component';

describe('ViewMasterTableComponent', () => {
  let component: ViewMasterTableComponent;
  let fixture: ComponentFixture<ViewMasterTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewMasterTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMasterTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
