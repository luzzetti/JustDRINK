import {Component, Input, OnInit} from '@angular/core';
import Map from 'ol/Map';
import {Feature, View} from "ol";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import {Draw} from "ol/interaction";
import {GeoJSON} from "ol/format";
import {RestaurantService} from "../../restaurant.service";
import {log} from "ol/console";
import {Polygon} from "ol/geom";
import {getCenter} from "ol/extent";
import {Fill, Stroke, Style} from "ol/style";
import {Position} from "geojson";

// https://dev.to/camptocamp-geo/openlayers-in-an-angular-application-mn1

@Component({
  selector: 'app-draw-feature',
  templateUrl: './draw-feature.component.html',
  styleUrls: ['./draw-feature.component.scss']
})
export class DrawFeatureComponent implements OnInit {

  @Input() restaurantId: string | undefined;

  private raster = new TileLayer({source: new OSM()});
  private theSource = new VectorSource({wrapX: false, format: new GeoJSON()});
  private vector = new VectorLayer({source: this.theSource})

  private lastDeliveryArea?: VectorLayer<VectorSource<any>>;

  /*  Note:
  * Il sistema di coordinate di default, è EPSG:3857
  */
  map: Map;

  private drawnPolygon: Feature | undefined;

  constructor(private _restaurantService: RestaurantService) {
    console.log("Warning...");
    this.map = new Map({
      layers: [this.raster, this.vector],
      view: new View({
        center: [1391683.7965990657, 5143642.832094591],
        zoom: 11,
      })
    });
  }

  ngOnInit(): void {
    if (!this.restaurantId) {
      throw new Error("Impossible to initialize this component without a provided ID");
    }

    this._restaurantService.getDeliveryAreaByRestaurantId(this.restaurantId)
    .subscribe(res => {
      log('Res: ', res);
      this.setLastDeliveryAreaFromCoordinates(res.polygon.coordinates);
    });
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

  saveShippingArea() {

    const lastDrawnGeometry = this.drawnPolygon?.getGeometry();

    if (!lastDrawnGeometry) {
      console.log("Non è stato ancora disegnato nessun poligono");
      return;
    }

    if (!this.restaurantId) {
      throw new Error("L'ID non può essere null");
    }

    const theGeoJson = new GeoJSON().writeGeometry(lastDrawnGeometry,
      {
        dataProjection: 'EPSG:4326',
        featureProjection: this.map?.getView().getProjection()
      });

    console.log('The GeoJSON in EPSG:4326: ', theGeoJson);

    this._restaurantService.updateDeliveryArea(this.restaurantId, theGeoJson)
    .subscribe(res => {
      console.log("Response: ", res)
      this.setLastDeliveryAreaFromCoordinates(res.polygon.coordinates);
    });

  }

  protected clearDrawing() {
    this.theSource.clear();
  }

  private setLastDeliveryAreaFromCoordinates(coordinates: Position[][]) {

    if (this.lastDeliveryArea) {
      this.map.removeLayer(this.lastDeliveryArea);
    }

    const geometry = new Polygon(coordinates).transform('EPSG:4326', 'EPSG:3857');
    console.log('Geom: ', geometry);

    let source = new VectorSource({
      features: [new Feature(geometry)]
    });

    this.lastDeliveryArea = new VectorLayer({
      source: source,
      style: new Style({
        fill: new Fill({
          color: 'rgba(255,0,0,0.10)'
        }),
        stroke: new Stroke({
          color: 'rgba(255,0,0,0.8)'
        }),
      })
    });

    this.map.addLayer(this.lastDeliveryArea);
    
    // Centra la mappa sul 'centroide' della geometria
    this.map.getView().setCenter(getCenter(geometry.getExtent()));

    // Zoomma la mappa per fittare la geometria
    this.map.getView().fit(geometry.getExtent());
  }
}
