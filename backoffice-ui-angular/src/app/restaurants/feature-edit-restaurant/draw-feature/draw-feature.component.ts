import {AfterViewInit, Component} from '@angular/core';
import Map from 'ol/Map';
import {View} from "ol";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import {Draw} from "ol/interaction";
import {GeoJSON} from "ol/format";

// https://dev.to/camptocamp-geo/openlayers-in-an-angular-application-mn1

@Component({
  selector: 'app-draw-feature',
  templateUrl: './draw-feature.component.html',
  styleUrls: ['./draw-feature.component.scss']
})
export class DrawFeatureComponent implements AfterViewInit {

  private raster = new TileLayer({source: new OSM()});
  private theSource = new VectorSource({wrapX: false});
  private vector = new VectorLayer({source: this.theSource})

  private map: Map | undefined;

  ngAfterViewInit(): void {

    // Target è l'ID del container dove verrà attaccata la mappa

    this.map = new Map({
      target: 'ol-map-container',
      layers: [this.raster, this.vector],
      view: new View({
        center: [0, 0],
        zoom: 5,
      })
    });


  }

  private draw: Draw = new Draw({
    source: this.theSource,
    type: 'Polygon',
  });

  public addInteraction() {

    this.theSource.clear();

    this.draw = new Draw({
      source: this.theSource,
      type: 'Polygon',
    });

    this.draw.on("drawend", (e) => {
      console.log('Fine disegno: ', e);

      let writer = new GeoJSON();
      // pass the feature as an array
      let geojsonStr = writer.writeFeatures([e.feature]);
      console.log(geojsonStr);

      this.map?.removeInteraction(this.draw);
    });

    this.map?.addInteraction(this.draw);

  }


  // let writer = new GeoJSON();
  //   pass the feature as an array
  // let geojsonStr = writer.writeFeatures([e.feature]);
  // console.log(geojsonStr);

}
