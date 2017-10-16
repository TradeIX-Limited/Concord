import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductorDashboardComponent } from './conductor-dashboard.component';

describe('ConductorDashboardComponent', () => {
  let component: ConductorDashboardComponent;
  let fixture: ComponentFixture<ConductorDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductorDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductorDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
