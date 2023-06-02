import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map, Observable} from "rxjs";
import {Restaurant, RestaurantAddress} from "./restaurant.model";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  // Potremmo usare un SUBJECT, se avessimo più componenti interessati al risultato della chiamata.
  // Se non è così, posso limitarmi a ritornare un observable

  constructor(private _httpClient: HttpClient) {
  }

  public getRestaurantById(restaurantId: string): Observable<Restaurant> {

    return this._httpClient.get<Restaurant>(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId)
    .pipe(map(res => res));

  }

  public updateDeliveryArea(restaurantId: string, deliveryArea: string): Observable<any> {

    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

    return this._httpClient
    .put(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId + "/shipping/area", deliveryArea, {headers: headers});

  }

  public getAddressOfRestaurantById(restaurantId: string): Observable<RestaurantAddress> {

    return this._httpClient.get<RestaurantAddress>(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId + '/address')
    .pipe(map(res => res));

  }

  public updateAddressOfRestaurant(restaurantId: string, theUpdatedAddress: RestaurantAddress): Observable<RestaurantAddress> {

    return this._httpClient
    .put<RestaurantAddress>(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId + "/address", theUpdatedAddress)
    .pipe(map(res => res));

  }

}
