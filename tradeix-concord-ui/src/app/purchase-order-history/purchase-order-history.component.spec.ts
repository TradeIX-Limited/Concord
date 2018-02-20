import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseOrderHistoryComponent } from './purchase-order-history.component';

describe('PurchaseOrderHistoryComponent', () => {
  let component: PurchaseOrderHistoryComponent;
  let fixture: ComponentFixture<PurchaseOrderHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PurchaseOrderHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseOrderHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
