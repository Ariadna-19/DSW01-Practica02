import { Injectable, signal } from '@angular/core';

type SessionState = {
  authenticated: boolean;
  userEmail: string | null;
  basicAuthToken: string | null;
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
    if (!state.authenticated || !state.basicAuthToken) {
      return null;
    }

    return `Basic ${state.basicAuthToken}`;
  }

  startSession(email: string, password: string): void {
    const next: SessionState = {
      authenticated: true,
      userEmail: email,
      basicAuthToken: btoa(`${email}:${password}`)
    };
    this.stateSignal.set(next);
    sessionStorage.setItem(STORAGE_KEY, JSON.stringify(next));
  }

  clearSession(): void {
    const next: SessionState = {
      authenticated: false,
      userEmail: null,
      basicAuthToken: null
    };
    this.stateSignal.set(next);
    sessionStorage.removeItem(STORAGE_KEY);
  }

  private loadInitialState(): SessionState {
    const stored = sessionStorage.getItem(STORAGE_KEY);
    if (!stored) {
      return { authenticated: false, userEmail: null, basicAuthToken: null };
    }

    try {
      const parsed = JSON.parse(stored) as SessionState;
      return {
        authenticated: Boolean(parsed.authenticated && parsed.userEmail && parsed.basicAuthToken),
        userEmail: parsed.userEmail ?? null,
        basicAuthToken: parsed.basicAuthToken ?? null
      };
    } catch {
      return { authenticated: false, userEmail: null, basicAuthToken: null };
    }
  }
}
