import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-working-hours',
  templateUrl: './working-hours.component.html',
  styleUrls: ['./working-hours.component.scss']
})
export class WorkingHoursComponent implements OnInit {

  public workingHoursForm: FormGroup = new FormGroup({
    // monday: new FormControl(null),
    // tuesday: new FormControl(null),
    // wednesday: new FormControl(null),
    // thursday: new FormControl(null),
    // friday: new FormControl(null),
    // saturday: new FormControl(null),
    // sunday: new FormControl(null),
  });
  isMondaySelected: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
    this.workingHoursForm.valueChanges.subscribe(
      vc => console.log(JSON.stringify(vc, null, 2))
    );
  }

  sendForm() {
    console.log('Send Father Form', this.workingHoursForm.value);
  }

}
