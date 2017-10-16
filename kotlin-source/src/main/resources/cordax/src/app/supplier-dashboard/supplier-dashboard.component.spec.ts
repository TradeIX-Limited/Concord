import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierDashboardComponent } from './supplier-dashboard.component';

describe('SupplierDashboardComponent', () => {
  let component: SupplierDashboardComponent;
  let fixture: ComponentFixture<SupplierDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplierDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplierDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
