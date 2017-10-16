import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPurchaseOrderDialogComponent } from './new-purchase-order-dialog.component';

describe('NewPurchaseOrderDialogComponent', () => {
  let component: NewPurchaseOrderDialogComponent;
  let fixture: ComponentFixture<NewPurchaseOrderDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPurchaseOrderDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPurchaseOrderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
