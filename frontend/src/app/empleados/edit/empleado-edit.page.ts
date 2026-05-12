import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { EmpleadosApiService } from '../data-access/empleados-api.service';
import { EmpleadoPayload } from '../domain/empleado.model';
import { EmpleadoFeedbackService } from '../ui/empleado-feedback.service';
import { EmpleadoFormComponent } from '../ui/empleado-form.component';

@Component({
  selector: 'app-empleado-edit-page',
  standalone: true,
  imports: [CommonModule, RouterLink, EmpleadoFormComponent],
  template: `
    <section class="page">
      <article class="card card--narrow">
        <div class="actions">
          <a class="btn btn-secondary" routerLink="/empleados">Volver</a>
        </div>
        <h1 class="title">Editar empleado</h1>
        <p class="subtitle">Actualiza los datos necesarios y guarda los cambios.</p>

        @if (initialValue(); as value) {
          <app-empleado-form
            [initialValue]="value"
            [requirePassword]="false"
            submitLabel="Guardar cambios"
            (formSubmit)="guardar($event)"
          />
        } @else {
          <p class="message message--info">Cargando...</p>
        }
      </article>
    </section>
  `
})
export class EmpleadoEditPage {
  private readonly api = inject(EmpleadosApiService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly feedback = inject(EmpleadoFeedbackService);

  private readonly clave = this.route.snapshot.paramMap.get('clave');
  readonly initialValue = signal<EmpleadoPayload | null>(null);

  constructor() {
    if (!this.clave) {
      this.router.navigate(['/empleados']);
      return;
    }

    this.api.detail(this.clave).subscribe({
      next: (emp) => {
        this.initialValue.set({
          nombre: emp.nombre,
          direccion: emp.direccion,
          telefono: emp.telefono,
          username: emp.username ?? '',
          password: '',
          departamentoId: emp.departamentoId ?? 1
        });
      },
      error: () => {
        this.feedback.error('No se pudo cargar el empleado para edición.');
        this.router.navigate(['/empleados']);
      }
    });
  }

  guardar(payload: EmpleadoPayload): void {
    if (!this.clave) {
      return;
    }

    this.api.update(this.clave, payload).subscribe({
      next: () => {
        this.feedback.success('Empleado actualizado correctamente.');
        this.router.navigate(['/empleados', this.clave]);
      },
      error: () => {
        this.feedback.error('No se pudo actualizar el empleado.');
      }
    });
  }
}
