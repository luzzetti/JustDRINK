import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {RestaurantService} from "../../restaurant.service";
import {RestaurantAddress} from "../../restaurant.model";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-address-information',
  templateUrl: './address-information.component.html',
  styleUrls: ['./address-information.component.scss']
})
export class AddressInformationComponent implements OnInit {

  @Input() restaurantId!: string;

  @ViewChild('f') addressInfoForm!: NgForm;

  private theAddress: RestaurantAddress | undefined;

  constructor(private _restaurantService: RestaurantService) {
  }

  ngOnInit(): void {
    console.log('Loading Address Info for restaurantId: ', this.restaurantId);

    if (!this.restaurantId) {
      throw new Error("The restaurantId is null");
    }

    this._restaurantService
    .getAddressOfRestaurantById(this.restaurantId)
    .subscribe(res => this.initAddressForm(res));
  }

  onSubmitChangeAddress() {

    const theUpdatedAddress: RestaurantAddress = {
      displayName: this.addressInfoForm.value.restaurantAddress,
      latitude: this.addressInfoForm.value.restaurantLatitude,
      longitude: this.addressInfoForm.value.restaurantLongitude
    }

    this._restaurantService
    .updateAddressOfRestaurant(this.restaurantId, theUpdatedAddress)
    .subscribe(res => this.initAddressForm(res));

  }

  onAddressInputted($event: Event) {
    this.addressInfoForm.form.patchValue({
      restaurantLatitude: null,
      restaurantLongitude: null,
    });
  }

  private initAddressForm(res: RestaurantAddress) {
    this.addressInfoForm.form.setValue({
      restaurantAddress: res.displayName,
      restaurantLatitude: res.latitude,
      restaurantLongitude: res.longitude,
    });
  }

}
