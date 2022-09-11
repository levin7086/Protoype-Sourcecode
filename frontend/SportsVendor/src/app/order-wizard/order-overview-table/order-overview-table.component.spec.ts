import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderOverviewTableComponent } from './order-overview-table.component';

describe('OrderOverviewTableComponent', () => {
  let component: OrderOverviewTableComponent;
  let fixture: ComponentFixture<OrderOverviewTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderOverviewTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderOverviewTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
