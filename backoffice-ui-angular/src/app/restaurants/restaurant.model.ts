import {Polygon} from "geojson";

export interface Restaurant {
  id: string,
  name: string,
  address: RestaurantAddress
}

export interface RestaurantAddress {
  displayName: string,
  latitude: number,
  longitude: number
}

export interface RestaurantDeliveryArea {
  polygon: Polygon
}
