import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantSectionsComponent } from './restaurant-sections.component';

describe('RestaurantSectionsComponent', () => {
  let component: RestaurantSectionsComponent;
  let fixture: ComponentFixture<RestaurantSectionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantSectionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestaurantSectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
