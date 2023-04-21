import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidatesGraphComponent } from './candidates-graph.component';

describe('CandidatesGraphComponent', () => {
  let component: CandidatesGraphComponent;
  let fixture: ComponentFixture<CandidatesGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandidatesGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CandidatesGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
