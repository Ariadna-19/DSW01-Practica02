import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthApiService } from '../data-access/auth-api.service';
import { AuthSessionService } from '../../core/auth/auth-session.service';
import { createLoginForm } from './login.form';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.page.html'
})
export class LoginPage {
  private readonly authApi = inject(AuthApiService);
  private readonly authSession = inject(AuthSessionService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly router = inject(Router);

  readonly loginForm = createLoginForm(this.formBuilder);
  readonly loading = signal(false);
  readonly errorMessage = signal<string | null>(null);

  constructor() {
    if (this.authSession.isAuthenticated) {
      this.router.navigate(['/empleados']);
    }
  }

  submit(): void {
    if (this.loginForm.invalid || this.loading()) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const payload = this.loginForm.getRawValue();
    this.errorMessage.set(null);
    this.loading.set(true);

    this.authApi.login(payload).subscribe({
      next: (response) => {
        this.loading.set(false);
        if (!response.authenticated) {
          this.errorMessage.set(response.message || 'Credenciales inválidas.');
          return;
        }

        this.authSession.startSession(payload.email, payload.password);
        this.router.navigate(['/empleados']);
      },
      error: () => {
        this.loading.set(false);
        this.errorMessage.set('No fue posible iniciar sesión.');
      }
    });
  }
}
