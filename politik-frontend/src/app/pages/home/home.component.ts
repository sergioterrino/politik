import { UserService } from 'src/app/services/user/user.service';
import { Component } from '@angular/core';
import { User } from 'src/app/models/User';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  users: User[] = [];
  filteredUsers: User[] = [];

  currentUser: User = new User(); //para poder obtener el user_id para el postIt()

  postDTO: any = {};
  text: string = ''; //a través de ngModel obtengo el texro escrito
  imagePath: string = ""; //esto lo obtendré cuando clicken en addImage, aún no sé cómo xd
  videoPath: string = ""; // = que imagePath

  constructor(private userService: UserService, private postService: PostService) { }

  ngOnInit() {
    this.getUsers();
    this.filteredUsers = [];
    this.currentUser = this.userService.getCurrentUser();
  }

  observerChangeSearch(value: string) {
    value = value.toLowerCase();
    this.filteredUsers = this.users.filter(user =>
      user.username.toLowerCase().startsWith(value) || user.name.toLowerCase().startsWith(value) ||
        user.lastname.toLowerCase().startsWith(value)
    );
    if (value == "" || value == null) {
      this.filteredUsers = [];
    }
  }

  getUsers() {
    this.userService.getUsersList().subscribe(data => {
      this.users = data;
    })
  }

  onSubmitPost() {
    //getImg
    //getVideo

    //creo el postDTO, objeto que contiene los datos que enviaré al Backend para crear el post en la db
    this.postDTO = {
      text: this.text,
      imagePath: this.imagePath,
      videoPath: this.videoPath
    }

    // llamo al método createPost() del Service para que envíe los datos
    this.postService.createPost(this.postDTO).subscribe({
      next: (response: any) => {
        console.log('response home.ts onSubmitPost() -> ', response);
      },
      error: (err : any) => {
        console.log('Error: onSubmitPost() - error al enviar los datos del post en Frontend', err);
      },
      complete: () => {
        console.log('Petición completa');
      }
    })

    console.log('texto escrito en el textarea: ', this.text);
  }


  //ajusta la altura del textarea cuando hay más de una línea
  adjustTextarea(event: any): void {
    let textarea: any = event.target; //obtiene el elemento <textarea>
    textarea.style.overflow = 'hidden';
    textarea.style.height = 'auto'; //el textarea cambia de tamaño para ajustarse al contenido
    textarea.style.height = textarea.scrollHeight + 'px';
  }
}
