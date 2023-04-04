import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RestaurantComponent} from './restaurant/restaurant.component';
import {
  RestaurantIntroCardComponent
} from './restaurant/restaurant-intro-card/restaurant-intro-card.component';
import {CardModule} from "primeng/card";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {MenubarModule} from "primeng/menubar";
import {AvatarModule} from "primeng/avatar";
import {RatingModule} from "primeng/rating";
import {FormsModule} from "@angular/forms";
import {DividerModule} from "primeng/divider";
import {AccordionModule} from "primeng/accordion";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {PanelModule} from "primeng/panel";
import { RestaurantSectionsComponent } from './restaurant/restaurant-sections/restaurant-sections.component';
import {TieredMenuModule} from "primeng/tieredmenu";
import {MenuModule} from "primeng/menu";
import { RestaurantBasketComponent } from './restaurant/restaurant-basket/restaurant-basket.component';
import {ButtonModule} from "primeng/button";

@NgModule({
  declarations: [
    AppComponent,
    RestaurantComponent,
    RestaurantIntroCardComponent,
    HeaderComponent,
    FooterComponent,
    RestaurantSectionsComponent,
    RestaurantBasketComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CardModule,
    MenubarModule,
    AvatarModule,
    RatingModule,
    FormsModule,
    DividerModule,
    AccordionModule,
    PanelModule,
    TieredMenuModule,
    MenuModule,
    ButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
