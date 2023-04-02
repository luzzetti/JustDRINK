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

@NgModule({
  declarations: [
    AppComponent,
    RestaurantComponent,
    RestaurantIntroCardComponent,
    HeaderComponent,
    FooterComponent
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
    PanelModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
