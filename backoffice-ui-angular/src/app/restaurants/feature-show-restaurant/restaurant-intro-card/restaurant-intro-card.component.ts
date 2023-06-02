import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {Restaurant} from "../../restaurant.model";

@Component({
  selector: 'app-restaurant-intro-card',
  templateUrl: './restaurant-intro-card.component.html',
  styleUrls: ['./restaurant-intro-card.component.scss']
})
export class RestaurantIntroCardComponent {

  @Input() theRestaurant: Restaurant | undefined;

  rating: number = 3;

  constructor(private _router: Router) {
  }

  navigateToRestaurantEditing() {
    if (!this.theRestaurant?.id) {
      throw new Error("The restaurantId is null");
    }

    const ignored =
      this._router.navigate(["/", "restaurants", this.theRestaurant?.id, "edit"])
  }

}
