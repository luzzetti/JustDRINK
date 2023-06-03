import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-working-hours',
  templateUrl: './working-hours.component.html',
  styleUrls: ['./working-hours.component.scss']
})
export class WorkingHoursComponent implements OnInit {

  weekdays: any = [
    {name: 'Monday'},
    {name: 'Tuesday'},
    {name: 'Wednesday'},
    {name: 'Thursday'},
    {name: 'Friday'},
    {name: 'Saturday'},
    {name: 'Sunday'},
  ]

  hoursForm: FormGroup = new FormGroup({
    'shifts': new FormArray([])
  })

  constructor() {
  }

  ngOnInit(): void {
    this.hoursForm.valueChanges.subscribe(v => {
      console.log(JSON.stringify(v));
    });

    const theControl = new FormControl(null);
    (<FormArray>this.hoursForm.get('shifts')).push(theControl);
  }

  addTimeslot() {
    console.log('Add Timeslot!');
    const theControl = new FormControl(null);
    (<FormArray>this.hoursForm.get('shifts')).push(theControl);
  }

  removeTimeslot(index: number) {
    (<FormArray>this.hoursForm.get('shifts')).removeAt(index);
  }

  getControls() {
    return (<FormArray>this.hoursForm.get('shifts')).controls;
  }

  onSubmit() {
    console.log('Form: ', this.hoursForm);
  }

}
