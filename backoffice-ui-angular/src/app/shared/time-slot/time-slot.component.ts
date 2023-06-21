import {Component, forwardRef} from '@angular/core';
import {
  AbstractControl,
  ControlValueAccessor,
  FormControl,
  FormGroup,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from "@angular/forms";

@Component({
  selector: 'app-time-slot',
  templateUrl: './time-slot.component.html',
  styleUrls: ['./time-slot.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TimeSlotComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => TimeSlotComponent),
      multi: true
    }
  ]
})
export class TimeSlotComponent implements ControlValueAccessor, Validator {

  openingForm: FormGroup = new FormGroup({
    from: new FormControl(null),
    through: new FormControl(null)
  });

  registerOnChange(fn: any): void {
    this.openingForm.valueChanges.subscribe(fn);
  }

  public onTouched: () => void = () => {
  };

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    isDisabled ? this.openingForm.disable() : this.openingForm.enable();
  }

  writeValue(val: any): void {
    val && this.openingForm.setValue(val, {emitEvent: false})
  }

  validate(control: AbstractControl): ValidationErrors | null {

    const fromValue = this.openingForm.get('from')?.value;
    const throughValue = this.openingForm.get('through')?.value;

    if (fromValue && throughValue) {
      return null;
    }

    if (!!fromValue) {
      return {
        invalidForm: {
          valid: false,
          message: "'from' value is missing"
        }
      }
    }

    if (!!throughValue) {
      return {
        invalidForm: {
          valid: false,
          message: "'through' value is missing"
        }
      }
    }

    // TODO: Implementare il controllo sul WeekDay

    return null;

  }

}
