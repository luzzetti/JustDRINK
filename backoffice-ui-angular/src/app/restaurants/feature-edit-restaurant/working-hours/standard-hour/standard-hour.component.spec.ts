import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StandardHourComponent } from './standard-hour.component';

describe('StandardHourComponent', () => {
  let component: StandardHourComponent;
  let fixture: ComponentFixture<StandardHourComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StandardHourComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StandardHourComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
