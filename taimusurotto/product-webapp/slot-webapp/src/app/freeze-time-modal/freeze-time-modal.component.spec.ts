import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreezeTimeModalComponent } from './freeze-time-modal.component';

describe('FreezeTimeModalComponent', () => {
  let component: FreezeTimeModalComponent;
  let fixture: ComponentFixture<FreezeTimeModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FreezeTimeModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FreezeTimeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
