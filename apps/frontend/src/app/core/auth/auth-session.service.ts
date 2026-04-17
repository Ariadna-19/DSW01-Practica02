import { Injectable, signal } from '@angular/core';

type SessionState = {
  authenticated: boolean;
  userEmail: string | null;
  password: string | null;
};

const STORAGE_KEY = 'frontend-auth-session';

@Injectable({ providedIn: 'root' })
export class AuthSessionService {
  private readonly stateSignal = signal<SessionState>(this.loadInitialState());

  readonly state = this.stateSignal.asReadonly();

  get isAuthenticated(): boolean {
    return this.stateSignal().authenticated;
  }

  get userEmail(): string | null {
    return this.stateSignal().userEmail;
  }

  get basicAuthHeader(): string | null {
    const state = this.stateSignal();
    if (!state.authenticated || !state.userEmail || !state.password) {
      return null;
    }

    return `Basic ${btoa(`${state.userEmail}:${state.password}`)}`;
  }

  startSession(email: string, password: string): void {
    const next: SessionState = {
      authenticated: true,
      userEmail: email,
      password
    };
    this.stateSignal.set(next);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(next));
  }

  clearSession(): void {
    const next: SessionState = {
      authenticated: false,
      userEmail: null,
      password: null
    };
    this.stateSignal.set(next);
    localStorage.removeItem(STORAGE_KEY);
  }

  private loadInitialState(): SessionState {
    const stored = localStorage.getItem(STORAGE_KEY);
    if (!stored) {
      return { authenticated: false, userEmail: null, password: null };
    }

    try {
      const parsed = JSON.parse(stored) as SessionState;
      return {
        authenticated: Boolean(parsed.authenticated && parsed.userEmail && parsed.password),
        userEmail: parsed.userEmail ?? null,
        password: parsed.password ?? null
      };
    } catch {
      return { authenticated: false, userEmail: null, password: null };
    }
  }
}
