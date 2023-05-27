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
  {path: '', redirectTo: '/restaurants/6accf60d-94b5-4363-a939-099d7dd67bca', pathMatch: 'full'},
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
