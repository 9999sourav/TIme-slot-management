import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDialogFormComponent } from './admin-dialog-form.component';

describe('AdminDialogFormComponent', () => {
  let component: AdminDialogFormComponent;
  let fixture: ComponentFixture<AdminDialogFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDialogFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDialogFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
