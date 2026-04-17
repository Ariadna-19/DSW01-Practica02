import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { AuthSessionService } from '../auth/auth-session.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.includes('/api/auth/login')) {
    return next(req);
  }

  const authSession = inject(AuthSessionService);
  const header = authSession.basicAuthHeader;

  if (!header) {
    return next(req);
  }

  return next(
    req.clone({
      setHeaders: {
        Authorization: header
      }
    })
  );
};
