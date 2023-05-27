import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private _httpClient: HttpClient) {
  }

  public getRestaurantById(restaurantId: string): void {

    this._httpClient.get<RestaurantResponse>(environment.BASE_URL + 'api/1.0/restaurants/' + restaurantId).subscribe(
      res => console.log("Response: ", res)
    );

  }

}

interface RestaurantResponse {
  id: string,
  name: string
}
