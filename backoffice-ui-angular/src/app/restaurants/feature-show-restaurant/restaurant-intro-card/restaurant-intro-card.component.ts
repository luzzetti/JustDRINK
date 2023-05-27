import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-restaurant-intro-card',
  templateUrl: './restaurant-intro-card.component.html',
  styleUrls: ['./restaurant-intro-card.component.scss']
})
export class RestaurantIntroCardComponent {

  @Input() restaurantId?: string;

  rating: number = 3;

  constructor(private _router: Router) {
  }

  navigateToRestaurantEditing() {
    const ignored =
      this._router.navigate(["/", "restaurants", this.restaurantId, "edit"])
  }
}
