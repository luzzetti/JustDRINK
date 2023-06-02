import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-opening',
  templateUrl: './opening.component.html',
  styleUrls: ['./opening.component.scss']
})
export class OpeningComponent implements OnInit {

  @Input() weekday: string | undefined;

  hoursForm: FormGroup = new FormGroup({
    'dayOfWeek': new FormControl(null),
    'shifts': new FormArray([])
  });

  constructor() {
  }

  ngOnInit(): void {
    console.log('Inputted: ', this.weekday);
    this.hoursForm.get('dayOfWeek')?.setValue(this.weekday);
  }

  addTimeslot() {
    const theControl = new FormControl(null);
    (<FormArray>this.hoursForm.get('shifts')).push(theControl);
  }

  removeTimeslot(index: number) {
    (<FormArray>this.hoursForm.get('shifts')).removeAt(index);
  }

  getControls() {
    return (<FormArray>this.hoursForm.get('shifts')).controls;
  }

}
