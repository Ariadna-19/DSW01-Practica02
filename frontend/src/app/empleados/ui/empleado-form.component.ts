import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { EmpleadoPayload } from '../domain/empleado.model';

@Component({
  selector: 'app-empleado-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <form class="grid" [formGroup]="form" (ngSubmit)="submit()">
      <div class="field">
        <label for="nombre">Nombre</label>
        <input id="nombre" formControlName="nombre" />
      </div>

      <div class="field">
        <label for="direccion">Dirección</label>
        <input id="direccion" formControlName="direccion" />
      </div>

      <div class="field">
        <label for="telefono">Teléfono</label>
        <input id="telefono" formControlName="telefono" />
      </div>

      <div class="field">
        <label for="username">Usuario (email)</label>
        <input id="username" formControlName="username" placeholder="usuario@empresa.com" />
      </div>

      <div class="field">
        <label for="password">Contraseña{{ requirePassword ? '' : ' (opcional)' }}</label>
        <input id="password" type="password" formControlName="password" />
        @if (form.controls.password.touched && form.controls.password.invalid) {
          <small>La contraseña debe tener al menos 8 caracteres.</small>
        }
      </div>

      <div class="field">
        <label for="departamentoId">Departamento ID</label>
        <input id="departamentoId" type="number" formControlName="departamentoId" />
      </div>

      <div class="actions">
        <button class="btn btn-primary" type="submit" [disabled]="form.invalid">{{ submitLabel }}</button>
      </div>
    </form>
  `
})
export class EmpleadoFormComponent {
  private readonly fb = inject(FormBuilder);

  @Input() submitLabel = 'Guardar';
  @Input() requirePassword = true;
  @Output() formSubmit = new EventEmitter<EmpleadoPayload>();

  readonly form = this.fb.nonNullable.group({
    nombre: ['', [Validators.required]],
    direccion: ['', [Validators.required]],
    telefono: ['', [Validators.required]],
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    departamentoId: [1, [Validators.required, Validators.min(1)]]
  });

  ngOnChanges(): void {
    if (this.requirePassword) {
      this.form.controls.password.setValidators([Validators.required, Validators.minLength(8)]);
    } else {
      this.form.controls.password.setValidators([Validators.minLength(8)]);
    }
    this.form.controls.password.updateValueAndValidity({ emitEvent: false });
  }

  @Input() set initialValue(value: EmpleadoPayload | null) {
    if (!value) {
      return;
    }
    this.form.patchValue(value);
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.formSubmit.emit(this.form.getRawValue());
  }
}
