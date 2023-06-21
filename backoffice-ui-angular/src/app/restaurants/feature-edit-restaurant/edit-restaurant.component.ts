import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-feature-edit-restaurant',
  templateUrl: './edit-restaurant.component.html',
  styleUrls: ['./edit-restaurant.component.scss']
})
export class EditRestaurantComponent {

  protected readonly theRestaurantId: string;

  protected readonly pageSections: MenuItem[];

  constructor(route: ActivatedRoute) {

    const theRestaurantId = route.snapshot.paramMap.get('restaurantId');

    if (!theRestaurantId) {
      throw new Error("the restaurant id cannot be null");
    }

    this.theRestaurantId = theRestaurantId;

    this.pageSections = [
      {label: 'Basic Info', icon: 'pi pi-id-card'},
      {label: 'Cuisines', icon: 'pi pi-download'},
      {label: 'Address', icon: 'pi pi-download'}
    ];
  }

}
