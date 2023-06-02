import {AfterViewInit, Component, Input} from '@angular/core';
import Map from 'ol/Map';
import {Feature, View} from "ol";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import {Draw} from "ol/interaction";
import {GeoJSON} from "ol/format";
import {RestaurantService} from "../../restaurant.service";
import {retry} from "rxjs";

// https://dev.to/camptocamp-geo/openlayers-in-an-angular-application-mn1

@Component({
  selector: 'app-draw-feature',
  templateUrl: './draw-feature.component.html',
  styleUrls: ['./draw-feature.component.scss']
})
export class DrawFeatureComponent implements AfterViewInit {

  @Input() restaurantId: string | undefined;

  private raster = new TileLayer({source: new OSM()});
  private theSource = new VectorSource({wrapX: false, format: new GeoJSON()});
  private vector = new VectorLayer({source: this.theSource})

  private map: Map | undefined;

  private drawnPolygon: Feature | undefined;

  constructor(private _restaurantService: RestaurantService) {

  }

  ngAfterViewInit(): void {

    // Target è l'ID del container dove verrà attaccata la mappa
    this.map = new Map({
      target: 'ol-map-container',
      layers: [this.raster, this.vector],
      view: new View({
        center: [1391683.7965990657, 5143642.832094591],
        zoom: 11,
      })
    });

    // Il sistema di coordinate di default, è EPSG:3857

  }

  private draw: Draw = new Draw({
    source: this.theSource,
    type: 'Polygon',
  });

  public addInteraction() {

    this.clearDrawing();

    this.draw = new Draw({
      source: this.theSource,
      type: 'Polygon',
    });

    // Il sistema di coordinate in cui voglio il GeoJSON, è EPSG:4326
    this.draw.on("drawend", (e) => {
      console.log('ESGI-3857: ', e.feature.getGeometry());

      this.drawnPolygon = e.feature;

      // projection: 'EPSG:4326',
      this.map?.removeInteraction(this.draw);
    });

    this.map?.addInteraction(this.draw);

  }


  // let writer = new GeoJSON();
  //   pass the feature as an array
  // let geojsonStr = writer.writeFeatures([e.feature]);
  // console.log(geojsonStr);

  saveShippingArea() {

    const lastDrownGeometry = this.drawnPolygon?.getGeometry();

    if (!lastDrownGeometry) {
      console.log("Non è stato ancora disegnato nessun poligono");
      return;
    }

    const theGeoJson = new GeoJSON().writeGeometry(lastDrownGeometry,
      {
        dataProjection: 'EPSG:4326',
        featureProjection: this.map?.getView().getProjection()
      });

    console.log('The GeoJSON in EPSG:4326: ', theGeoJson);

    if (!this.restaurantId) {
      throw new Error("L'ID non può essere null");
    }

    this._restaurantService.updateDeliveryArea(this.restaurantId, theGeoJson)
    .pipe(
      retry(2),
    )
    .subscribe(res => {
      console.log("Response: ", res)
    });

  }

  protected clearDrawing() {
    this.theSource.clear();
  }


}
