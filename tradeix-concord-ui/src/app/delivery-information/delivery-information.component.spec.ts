import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryInformationComponent } from './delivery-information.component';

describe('DeliveryInformationComponent', () => {
  let component: DeliveryInformationComponent;
  let fixture: ComponentFixture<DeliveryInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveryInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
