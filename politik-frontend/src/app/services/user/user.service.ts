import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //Esta URL obtine el listado de todos los usuarios en el backend
  private baseURL = 'http://localhost:8080/api/v1/users';

  constructor(private httpClient: HttpClient) { }

  //Este metodo obtiene todos los usuarios del backend
  getUsersList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseURL}`);
  }

  //Comrpobar si un usuario existe en el backend byPhone
  getUserByPhone(phone: string): Observable<User> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<User>(`${this.baseURL}/phone`, phone, { headers: headers });
  }
}
