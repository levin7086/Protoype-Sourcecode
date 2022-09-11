import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompleteOrderWizardStepComponent } from './complete-order-wizard-step.component';

describe('CompleteOrderWizardStepComponent', () => {
  let component: CompleteOrderWizardStepComponent;
  let fixture: ComponentFixture<CompleteOrderWizardStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompleteOrderWizardStepComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompleteOrderWizardStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
