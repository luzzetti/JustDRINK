import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {
  ShowRestaurantComponent
} from './restaurants/feature-show-restaurant/show-restaurant.component';
import {
  RestaurantIntroCardComponent
} from './restaurants/feature-show-restaurant/restaurant-intro-card/restaurant-intro-card.component';
import {CardModule} from "primeng/card";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {MenubarModule} from "primeng/menubar";
import {AvatarModule} from "primeng/avatar";
import {RatingModule} from "primeng/rating";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DividerModule} from "primeng/divider";
import {AccordionModule} from "primeng/accordion";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {PanelModule} from "primeng/panel";
import {
  RestaurantSectionsComponent
} from './restaurants/feature-show-restaurant/restaurant-sections/restaurant-sections.component';
import {TieredMenuModule} from "primeng/tieredmenu";
import {MenuModule} from "primeng/menu";
import {
  RestaurantBasketComponent
} from './restaurants/feature-show-restaurant/restaurant-basket/restaurant-basket.component';
import {ButtonModule} from "primeng/button";
import {
  EditRestaurantComponent
} from './restaurants/feature-edit-restaurant/edit-restaurant.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {HttpClientModule} from "@angular/common/http";
import {FieldsetModule} from "primeng/fieldset";
import {TabViewModule} from "primeng/tabview";
import {
  DrawFeatureComponent
} from './restaurants/feature-edit-restaurant/draw-feature/draw-feature.component';
import {
  WorkingHoursComponent
} from './restaurants/feature-edit-restaurant/working-hours/working-hours.component';
import {InputSwitchModule} from "primeng/inputswitch";
import {CalendarModule} from "primeng/calendar";
import {SelectButtonModule} from "primeng/selectbutton";
import { StandardHourComponent } from './restaurants/feature-edit-restaurant/working-hours/standard-hour/standard-hour.component';
import { GeneralInformationComponent } from './restaurants/feature-edit-restaurant/general-information/general-information.component';
import {InplaceModule} from "primeng/inplace";
import {InputTextModule} from "primeng/inputtext";
import { AddressInformationComponent } from './restaurants/feature-edit-restaurant/address-information/address-information.component';

@NgModule({
  declarations: [
    AppComponent,
    ShowRestaurantComponent,
    RestaurantIntroCardComponent,
    HeaderComponent,
    FooterComponent,
    RestaurantSectionsComponent,
    RestaurantBasketComponent,
    EditRestaurantComponent,
    NotFoundComponent,
    DrawFeatureComponent,
    WorkingHoursComponent,
    StandardHourComponent,
    GeneralInformationComponent,
    AddressInformationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    CardModule,
    MenubarModule,
    AvatarModule,
    RatingModule,
    FormsModule,
    ReactiveFormsModule,
    DividerModule,
    AccordionModule,
    PanelModule,
    TieredMenuModule,
    MenuModule,
    ButtonModule,
    FieldsetModule,
    TabViewModule,
    InputSwitchModule,
    CalendarModule,
    SelectButtonModule,
    InplaceModule,
    InputTextModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
