import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {RestaurantService} from "../restaurant.service";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-feature-edit-restaurant',
  templateUrl: './edit-restaurant.component.html',
  styleUrls: ['./edit-restaurant.component.scss']
})
export class EditRestaurantComponent implements OnInit {

  private readonly restaurantId: string;
  protected readonly pageSections: MenuItem[];

  constructor(route: ActivatedRoute, private _restaurantService: RestaurantService) {
    const theRestaurantId = route.snapshot.paramMap.get('restaurantId');
    if (!theRestaurantId) {
      throw new Error("the restaurant id cannot be null");
    }
    this.restaurantId = theRestaurantId;

    this.pageSections = [
      {label: 'Basic Info', icon: 'pi pi-id-card'},
      {label: 'Cuisines', icon: 'pi pi-download'},
      {label: 'Address', icon: 'pi pi-download'}
    ];
  }

  ngOnInit(): void {
    console.log('Loading data for restaurantId: ', this.restaurantId);
    if (!this.restaurantId) {
      throw new Error("The restaurantId is null");
    }
    this._restaurantService.getRestaurantById(this.restaurantId);
  }

}
