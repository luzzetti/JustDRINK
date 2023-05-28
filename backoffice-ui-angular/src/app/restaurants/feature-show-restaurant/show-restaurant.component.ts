import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {RestaurantService} from "../restaurant.service";

@Component({
  selector: 'app-show-restaurant',
  templateUrl: './show-restaurant.component.html',
  styleUrls: ['./show-restaurant.component.scss']
})
export class ShowRestaurantComponent implements OnInit {

  protected readonly restaurantId: string;

  constructor(private _route: ActivatedRoute, private _restaurantService: RestaurantService) {
    const theRestaurantId = _route.snapshot.paramMap.get('restaurantId');
    if (!theRestaurantId) {
      throw new Error("Invalid Id");
    }

    this.restaurantId = theRestaurantId;
  }

  ngOnInit(): void {
    console.log('Loading data for restaurantId: ', this.restaurantId);
    if (!this.restaurantId) {
      throw new Error("The restaurantId is null");
    }
    this._restaurantService.getRestaurantById(this.restaurantId);
  }


}
