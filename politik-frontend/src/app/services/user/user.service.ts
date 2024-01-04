import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //Esta URL obtine el listado de todos los usuarios en el backend
  private baseURL = 'http://localhost:8080/users';
  private currentUser!: User;

  constructor(private httpClient: HttpClient, private router: Router) { }

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

  //mando al backend el username y el password para que compruebe si es correcto
  login(namePhoneEmail: string, password: string, index: number): Observable<any>{
    return this.httpClient.post<any>(`${this.baseURL}/login`, {namePhoneEmail, password, index});
  }

  //cerrar sesion -> borro el token del localStorage y redirijo a start.page
  logout() {
    localStorage.removeItem('jwt');
    this.router.navigate(['/start']);
  }

  setCurrentUser(user: User) {
    this.currentUser = user;
    //para que no se pierdan cuando la página se recarga, lo guardo en el localStorage
    localStorage.setItem('currentUser', JSON.stringify(user));
    //
  }

  getCurrentUser() {
    if (!this.currentUser) {
      const storedUser = localStorage.getItem('currentUser');
      if (storedUser) {
        this.currentUser = JSON.parse(storedUser);
      }
    }
    return this.currentUser;
  }

}
