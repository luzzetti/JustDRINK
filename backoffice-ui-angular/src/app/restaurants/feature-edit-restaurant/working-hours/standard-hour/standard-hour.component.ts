import {Component, forwardRef, Input} from '@angular/core';
import {
  AbstractControl,
  ControlValueAccessor,
  FormControl,
  FormGroup,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator,
  Validators
} from "@angular/forms";

@Component({
  selector: 'app-standard-hour',
  templateUrl: './standard-hour.component.html',
  styleUrls: ['./standard-hour.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => StandardHourComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => StandardHourComponent),
      multi: true
    }
  ]
})
export class StandardHourComponent implements ControlValueAccessor, Validator {

  @Input() labelName: string = '';

  standardHourForm: FormGroup = new FormGroup({
    isOpen: new FormControl(false, [Validators.required]),
    from: new FormControl(null, []),
    through: new FormControl(null, [])
  });

  constructor() {
  }

  /***
   * Da qui in avanti...solo i leoni
   * https://medium.com/angular-in-depth/angular-nested-reactive-forms-using-cvas-b394ba2e5d0d
   */

  // Validator impl - Logica per decidere se questo form is valido oppure no
  validate(control: AbstractControl<any, any>): ValidationErrors | null {
    const isOpenValue = this.standardHourForm.get('isOpen')?.value;
    const fromValue = this.standardHourForm.get('from')?.value;
    const throughValue = this.standardHourForm.get('through')?.value;

    if (isOpenValue && fromValue && throughValue) {
      return null;
    }

    // Valid
    if (!isOpenValue) {
      return null;
    }

    return {
      invalidForm: {
        valid: false,
        message: "Fields are invalid"
      }
    };

  }

  // Control Access Value impl - Tipo l'editor di GWT
  registerOnChange(fn: any): void {
    this.standardHourForm.valueChanges.subscribe(fn);
  }

  public onTouched: () => void = () => {
  };

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  writeValue(val: any): void {
    val && this.standardHourForm.setValue(val, {emitEvent: false})
  }

  setDisabledState(isDisabled: boolean): void {
    isDisabled ? this.standardHourForm.disable() : this.standardHourForm.enable();
  }

}
