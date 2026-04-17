import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../core/http/api-client.service';
import { Empleado, EmpleadoPageResponse, EmpleadoPayload } from '../domain/empleado.model';

@Injectable({ providedIn: 'root' })
export class EmpleadosApiService {
  private readonly apiClient = inject(ApiClientService);

  list(page = 0): Observable<EmpleadoPageResponse> {
    return this.apiClient.get<EmpleadoPageResponse>(`/api/empleados?page=${page}`);
  }

  detail(clave: string): Observable<Empleado> {
    return this.apiClient.get<Empleado>(`/api/empleados/${clave}`);
  }

  create(payload: EmpleadoPayload): Observable<Empleado> {
    return this.apiClient.post<Empleado>('/api/empleados', payload);
  }

  update(clave: string, payload: EmpleadoPayload): Observable<Empleado> {
    return this.apiClient.put<Empleado>(`/api/empleados/${clave}`, payload);
  }

  delete(clave: string): Observable<void> {
    return this.apiClient.delete(`/api/empleados/${clave}`);
  }
}
