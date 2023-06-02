import {ChangeDetectionStrategy, Component, ElementRef, Input, OnInit} from '@angular/core';
import Map from 'ol/Map'

@Component({
  selector: 'app-map-viewer',
  template: '',
  styles: [':host { width: 100%; height: 100%; display: block; }',
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MapViewerComponent implements OnInit {

  @Input() map?: Map;

  constructor(private elementRef: ElementRef) {
    /*
    * To render the map in the component, we inject the ElementRef in the constructor,
    * which is a reference to the root element of the component itself.
    */
  }

  ngOnInit(): void {
    this.map?.setTarget(this.elementRef.nativeElement);
  }

}
