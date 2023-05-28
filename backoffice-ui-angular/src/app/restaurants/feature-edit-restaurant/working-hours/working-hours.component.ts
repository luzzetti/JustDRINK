import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-working-hours',
  templateUrl: './working-hours.component.html',
  styleUrls: ['./working-hours.component.scss']
})
export class WorkingHoursComponent implements OnInit {

  debug: string = '';

  public workingHoursForm: FormGroup = new FormGroup({
    monday: new FormControl(null),
    tuesday: new FormControl(null),
    wednesday: new FormControl(null),
    thursday: new FormControl(null),
    friday: new FormControl(null),
    saturday: new FormControl(null),
    sunday: new FormControl(null),
  });

  constructor() {
  }

  ngOnInit(): void {
    this.workingHoursForm.valueChanges.subscribe(
      vc => this.debug = JSON.stringify(vc, null, 2)
    );
  }

  sendForm() {
    console.log('Send Father Form', this.workingHoursForm.value);
  }

}
