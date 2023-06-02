import {Component} from '@angular/core';
import {FormArray, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-working-hours',
  templateUrl: './working-hours.component.html',
  styleUrls: ['./working-hours.component.scss']
})
export class WorkingHoursComponent {

  weekdays: any = [
    'Monday',
    'Tuesday',
    'Wednesday',
    'Thursday',
    'Friday',
    'Saturday',
    'Sunday'
  ]

  openingsForm: FormGroup = new FormGroup({
    'openings': new FormArray([])
  });

}
