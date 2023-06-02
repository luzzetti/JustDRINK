import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {RestaurantService} from "../../restaurant.service";
import {NgForm} from "@angular/forms";
import {Restaurant} from "../../restaurant.model";

@Component({
  selector: 'app-general-information',
  templateUrl: './general-information.component.html',
  styleUrls: ['./general-information.component.scss']
})
export class GeneralInformationComponent implements OnInit {

  @Input() restaurantId!: string;

  @ViewChild('f') generalInfoForm?: NgForm;

  private theRestaurant: Restaurant | undefined;

  constructor(private _restaurantService: RestaurantService) {
  }

  ngOnInit(): void {
    console.log('Loading General Info for restaurantId: ', this.restaurantId);

    if (!this.restaurantId) {
      throw new Error("The restaurantId is null");
    }

    this._restaurantService.getRestaurantById(this.restaurantId)
    .subscribe(res => {
      // Form initialization
      this.generalInfoForm?.form.reset({
        restaurantName: res.name,
      });

    });
  }

  onSubmitChangeInfo(): void {
    console.log("Submitted: ", this.generalInfoForm);
    throw Error("Non implementato");
  }
}
