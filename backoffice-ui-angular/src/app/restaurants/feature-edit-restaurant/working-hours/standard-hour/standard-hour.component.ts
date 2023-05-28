import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-standard-hour',
  templateUrl: './standard-hour.component.html',
  styleUrls: ['./standard-hour.component.scss']
})
export class StandardHourComponent {

  standardHourForm: FormGroup = new FormGroup({
    isOpen: new FormControl("", [Validators.required])
  });

}
