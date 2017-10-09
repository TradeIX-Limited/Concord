import { TestBed, inject } from '@angular/core/testing';

import { PurchaseOrderService } from './purchase-order.service';

describe('PurchaseOrderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PurchaseOrderService]
    });
  });

  it('should be created', inject([PurchaseOrderService], (service: PurchaseOrderService) => {
    expect(service).toBeTruthy();
  }));
});
