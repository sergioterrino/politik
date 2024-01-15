import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // console.log('JwtInterceptor is running--------------------------');
    // Obtener el token del almacenamiento local
    let token = localStorage.getItem('token');

    if (token) {
      // Clonar la solicitud y añadir el encabezado de autorización
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    // console.log('request -> ', request.headers.get('Authorization'));

    return next.handle(request);
  }
}
