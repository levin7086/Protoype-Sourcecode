import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentWizardStepComponent } from './payment-wizard-step.component';

describe('PaymentWizardStepComponent', () => {
  let component: PaymentWizardStepComponent;
  let fixture: ComponentFixture<PaymentWizardStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentWizardStepComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentWizardStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
