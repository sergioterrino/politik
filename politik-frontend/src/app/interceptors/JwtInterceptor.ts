import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler) {
        // Obtener el token del almacenamiento local
        let jwt = localStorage.getItem('jwt');

        if (jwt) {
            // Clonar la solicitud y añadir el encabezado de autorización
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${jwt}`
                }
            });
        }

        return next.handle(request);
    }
}
