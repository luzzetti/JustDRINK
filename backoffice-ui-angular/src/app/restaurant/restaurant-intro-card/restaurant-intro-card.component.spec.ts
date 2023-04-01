import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantIntroCardComponent } from './restaurant-intro-card.component';

describe('RestaurantIntroCardComponent', () => {
  let component: RestaurantIntroCardComponent;
  let fixture: ComponentFixture<RestaurantIntroCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantIntroCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestaurantIntroCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
