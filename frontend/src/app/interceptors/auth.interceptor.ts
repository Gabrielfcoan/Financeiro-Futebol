import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { retry } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  if (token) {
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(cloned).pipe(retry({ count: 2, delay: 1000 }));
  }
  return next(req).pipe(retry({ count: 2, delay: 1000 }));
};
