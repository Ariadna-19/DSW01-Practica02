import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { EmpleadosApiService } from '../data-access/empleados-api.service';
import { Empleado } from '../domain/empleado.model';
import { EmpleadoFeedbackService } from '../ui/empleado-feedback.service';

@Component({
  selector: 'app-empleado-detail-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './empleado-detail.page.html'
})
export class EmpleadoDetailPage {
  private readonly api = inject(EmpleadosApiService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly feedback = inject(EmpleadoFeedbackService);

  readonly empleado = signal<Empleado | null>(null);
  readonly loading = signal(true);

  constructor() {
    const clave = this.route.snapshot.paramMap.get('clave');
    if (!clave) {
      this.router.navigate(['/empleados']);
      return;
    }
    this.api.detail(clave).subscribe({
      next: (value) => {
        this.loading.set(false);
        this.empleado.set(value);
      },
      error: () => {
        this.loading.set(false);
        this.feedback.error('No se encontró el empleado.');
        this.router.navigate(['/empleados']);
      }
    });
  }

  eliminar(): void {
    const current = this.empleado();
    if (!current) {
      return;
    }
    const confirmed = window.confirm(`¿Eliminar empleado ${current.clave}?`);
    if (!confirmed) {
      return;
    }
    this.api.delete(current.clave).subscribe({
      next: () => {
        this.feedback.success('Empleado eliminado correctamente.');
        this.router.navigate(['/empleados']);
      },
      error: () => {
        this.feedback.error('No se pudo eliminar el empleado.');
      }
    });
  }
}
