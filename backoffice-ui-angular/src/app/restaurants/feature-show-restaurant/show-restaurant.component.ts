import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {RestaurantService} from "../restaurant.service";
import {Restaurant} from "../restaurant.model";

@Component({
  selector: 'app-show-restaurant',
  templateUrl: './show-restaurant.component.html',
  styleUrls: ['./show-restaurant.component.scss']
})
export class ShowRestaurantComponent implements OnInit {

  protected readonly restaurantId: string;
  protected theRestaurant: Restaurant | undefined;

  constructor(private _route: ActivatedRoute,
              private _restaurantService: RestaurantService) {
    const theRestaurantId = _route.snapshot.paramMap.get('restaurantId');
    if (!theRestaurantId) {
      throw new Error("Invalid Id");
    }

    this.restaurantId = theRestaurantId;
  }

  ngOnInit(): void {

    if (!this.restaurantId) {
      throw new Error("The restaurantId is null");
    }
    this._restaurantService.getRestaurantById(this.restaurantId).subscribe(res => {
      this.theRestaurant = res;
    });
  }


}
