import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-working-hours',
  templateUrl: './working-hours.component.html',
  styleUrls: ['./working-hours.component.scss']
})
export class WorkingHoursComponent implements OnInit, OnDestroy {

  protected istantanea: string = '';

  workingHoursForm: FormGroup = new FormGroup<any>({

    'monday': new FormGroup({
      'isOpen': new FormControl(null, Validators.required),
      'hourFrom': new FormControl(),
      'hourTo': new FormControl()
    }),
    'tuesday': new FormGroup({
      'isOpen': new FormControl(null, Validators.required),
      'hourFrom': new FormControl(),
      'hourTo': new FormControl()
    })


  });

  constructor() {
  }

  ngOnInit(): void {
    this.workingHoursForm.valueChanges
    .subscribe(vc => this.istantanea = JSON.stringify(vc, null, 2))
  }

  ngOnDestroy(): void {
  }

  onSubmit() {
    console.log(this.workingHoursForm);
  }

}
