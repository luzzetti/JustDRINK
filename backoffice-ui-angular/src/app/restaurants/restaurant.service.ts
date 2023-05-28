import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private _httpClient: HttpClient) {
  }

  public getRestaurantById(restaurantId: string): void {

    this._httpClient.get<RestaurantResponse>(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId)
    .subscribe(res => console.log("Response: ", res));
  }

  public updateDeliveryArea(restaurantId: string, deliveryArea: string) {

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

    this._httpClient
    .put(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId + "/shipping/area", deliveryArea, {headers: headers})
    .subscribe(res => console.log("Response: ", res));

  }


}

interface RestaurantResponse {
  id: string,
  name: string
}
