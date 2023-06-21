import {Component} from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-restaurant-sections',
  templateUrl: './restaurant-sections.component.html',
  styleUrls: ['./restaurant-sections.component.scss']
})
export class RestaurantSectionsComponent {

  items: MenuItem[] = [
    {label: 'Pizza Rossa', routerLink: ['#Pizza-rossa']},
    {label: 'Pizza Blu', routerLink: ['#']},
    {label: 'Pizza Bianca', routerLink: ['#']},
    {label: 'Pizza Gialla', routerLink: ['#']},
    {label: 'Pizza Verde', routerLink: ['#']}
  ];


}
