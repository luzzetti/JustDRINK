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
