import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShippingWizardStepComponent } from './shipping-wizard-step.component';

describe('ShippingWizardStepComponent', () => {
  let component: ShippingWizardStepComponent;
  let fixture: ComponentFixture<ShippingWizardStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShippingWizardStepComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShippingWizardStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
