import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseURL: string = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) { }

  getAllPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.baseURL);
  }

  //recibe la respuesta ok del backend > PostController > createPost()
  createPost(postDTO: any): Observable<any> {
    return this.httpClient.post<any>(`${this.baseURL}/create`, postDTO);
  }

  getPostsByUserId(userId: number): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.baseURL}/user/${userId}`);
  }
}
