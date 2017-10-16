import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeOwnerDialogComponent } from './change-owner-dialog.component';

describe('ChangeOwnerDialogComponent', () => {
  let component: ChangeOwnerDialogComponent;
  let fixture: ComponentFixture<ChangeOwnerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeOwnerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeOwnerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
