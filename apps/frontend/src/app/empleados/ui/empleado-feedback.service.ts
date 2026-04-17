import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class EmpleadoFeedbackService {
  private readonly messageSignal = signal<string | null>(null);

  readonly message = this.messageSignal.asReadonly();

  success(message: string): void {
    this.messageSignal.set(message);
  }

  error(message: string): void {
    this.messageSignal.set(message);
  }

  clear(): void {
    this.messageSignal.set(null);
  }
}
