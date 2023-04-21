import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSlotsViewComponent } from './add-slots-view.component';

describe('AddSlotsViewComponent', () => {
  let component: AddSlotsViewComponent;
  let fixture: ComponentFixture<AddSlotsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSlotsViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSlotsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
