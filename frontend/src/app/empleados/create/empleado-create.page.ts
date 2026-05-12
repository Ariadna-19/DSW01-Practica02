import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { EmpleadosApiService } from '../data-access/empleados-api.service';
import { EmpleadoPayload } from '../domain/empleado.model';
import { EmpleadoFeedbackService } from '../ui/empleado-feedback.service';
import { EmpleadoFormComponent } from '../ui/empleado-form.component';

@Component({
  selector: 'app-empleado-create-page',
  standalone: true,
  imports: [CommonModule, RouterLink, EmpleadoFormComponent],
  template: `
    <section class="page">
      <article class="card card--narrow">
        <div class="actions">
          <a class="btn btn-secondary" routerLink="/empleados">Volver</a>
        </div>
        <h1 class="title">Nuevo empleado</h1>
        <p class="subtitle">Completa los datos del empleado y guarda.</p>
        <app-empleado-form submitLabel="Crear" (formSubmit)="crear($event)" />
      </article>
    </section>
  `
})
export class EmpleadoCreatePage {
  private readonly api = inject(EmpleadosApiService);
  private readonly router = inject(Router);
  private readonly feedback = inject(EmpleadoFeedbackService);

  crear(payload: EmpleadoPayload): void {
    this.api.create(payload).subscribe({
      next: (created) => {
        this.feedback.success('Empleado creado correctamente.');
        this.router.navigate(['/empleados', created.clave]);
      },
      error: (error) => {
        this.feedback.error(this.getErrorMessage(error));
      }
    });
  }

  private getErrorMessage(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
      const response = error.error as { message?: string } | string | null;

      if (typeof response === 'string' && response.trim()) {
        return response;
      }

      if (response && typeof response === 'object' && typeof response.message === 'string' && response.message.trim()) {
        return response.message;
      }
    }

    return 'No se pudo crear el empleado.';
  }
}
