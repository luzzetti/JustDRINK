import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {
  ShowRestaurantComponent
} from "./restaurants/feature-show-restaurant/show-restaurant.component";
import {
  EditRestaurantComponent
} from "./restaurants/feature-edit-restaurant/edit-restaurant.component";
import {NotFoundComponent} from "./not-found/not-found.component";

const routes: Routes = [
  {path: '', redirectTo: '/restaurants/87d5953f-95dd-485d-9738-adf679688cd6', pathMatch: 'full'},
  {path: 'restaurants/:restaurantId', component: ShowRestaurantComponent},
  {path: 'restaurants/:restaurantId/edit', component: EditRestaurantComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
