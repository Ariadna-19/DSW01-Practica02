import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../core/http/api-client.service';

export type LoginPayload = {
  email: string;
  password: string;
};

export type LoginResponse = {
  authenticated: boolean;
  empleadoClave: string | null;
  message: string;
};

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private readonly apiClient = inject(ApiClientService);

  login(payload: LoginPayload): Observable<LoginResponse> {
    return this.apiClient.post<LoginResponse>('/api/auth/login', payload);
  }
}
