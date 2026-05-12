import { FormBuilder, Validators } from '@angular/forms';

export function createLoginForm(formBuilder: FormBuilder) {
  return formBuilder.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });
}
