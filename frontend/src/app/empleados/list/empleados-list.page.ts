import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AuthSessionService } from '../../core/auth/auth-session.service';
import { EmpleadosApiService } from '../data-access/empleados-api.service';
import { Empleado } from '../domain/empleado.model';
import { EmpleadoFeedbackService } from '../ui/empleado-feedback.service';

@Component({
  selector: 'app-empleados-list-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './empleados-list.page.html'
})
export class EmpleadosListPage {
  private readonly api = inject(EmpleadosApiService);
  private readonly router = inject(Router);
  private readonly session = inject(AuthSessionService);
  readonly feedback = inject(EmpleadoFeedbackService);

  readonly empleados = signal<Empleado[]>([]);
  readonly loading = signal(true);

  constructor() {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.api.list().subscribe({
      next: (response) => {
        this.loading.set(false);
        this.empleados.set(response.content ?? []);
      },
      error: () => {
        this.loading.set(false);
        this.feedback.error('No se pudieron cargar los empleados.');
      }
    });
  }

  logout(): void {
    this.session.clearSession();
    this.router.navigate(['/login']);
  }
}
