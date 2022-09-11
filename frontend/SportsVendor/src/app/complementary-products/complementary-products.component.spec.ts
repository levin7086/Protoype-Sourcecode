import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplementaryProductsComponent } from './complementary-products.component';

describe('ComplementaryProductsComponent', () => {
  let component: ComplementaryProductsComponent;
  let fixture: ComponentFixture<ComplementaryProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComplementaryProductsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComplementaryProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
