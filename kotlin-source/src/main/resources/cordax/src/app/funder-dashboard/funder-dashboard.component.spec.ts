import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FunderDashboardComponent } from './funder-dashboard.component';

describe('FunderDashboardComponent', () => {
  let component: FunderDashboardComponent;
  let fixture: ComponentFixture<FunderDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FunderDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FunderDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
