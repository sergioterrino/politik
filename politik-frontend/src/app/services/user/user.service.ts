import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //Esta URL obtine el listado de todos los usuarios en el backend
  private baseURL = 'http://localhost:8080/users';

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

  //checkeo si existe algun usuario con el mismo email
  getUserByEmail(email: string): Observable<User> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<User>(`${this.baseURL}/email`, email, { headers: headers });
  }

  getUserByUsername(username: string): Observable<User>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<User>(`${this.baseURL}/username`, username, {headers: headers});
  }

  //mandará el userDTO al userController del backend para que setee los datos y los almacene en la base de datos
  signup(userDTO: any): Observable<any>{
    return this.httpClient.post<any>(`${this.baseURL}/signup`, userDTO);
  }



}
