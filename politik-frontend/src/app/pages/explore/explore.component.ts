import { UserService } from './../../services/user/user.service';
import { Component, ElementRef } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime } from 'rxjs';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.scss']
})
export class ExploreComponent {

  users: User[] = [];
  filteredUsers: User[] = [];

  constructor(private userService: UserService, private elementRef: ElementRef) { }

  ngOnInit(): void {
    this.getUsers();
    this.filteredUsers = [];
  }

  ngOnAfterViewInit(): void {
  }

  observerChangeSearch(value: string) {
    value = value.toLowerCase();
    this.filteredUsers = this.users.filter(user =>
      user.username.toLowerCase().startsWith(value) || user.name.toLowerCase().startsWith(value)
      || user.lastname.toLowerCase().startsWith(value)
    );
    if (value == "" || value == null) {
      this.filteredUsers = [];
    }
    console.log('Resultados de búsqueda:', this.filteredUsers);
  }

  getUsers() {
    this.userService.getUsersList().subscribe(data => {
      this.users = data;
    })
  }

  //para que cuando clicke en el inputSearch se cambien los estilos.
  onFocus() {
    this.elementRef.nativeElement.querySelector('#navbar').classList.add('focused');
    this.elementRef.nativeElement.querySelector('#i').classList.remove('text-secondary');
    this.elementRef.nativeElement.querySelector('#i').classList.add('text-primary');
  }
  onBlur() {
    this.elementRef.nativeElement.querySelector('#navbar').classList.remove('focused');
    this.elementRef.nativeElement.querySelector('#i').classList.remove('text-primary');
  }

}














// import { UserService } from './../../services/user/user.service';
// import { Component, ElementRef } from '@angular/core';
// import { FormControl } from '@angular/forms';
// import { debounceTime } from 'rxjs';
// import { User } from 'src/app/models/user';

// @Component({
//   selector: 'app-explore',
//   templateUrl: './explore.component.html',
//   styleUrls: ['./explore.component.scss']
// })
// export class ExploreComponent {

//   control = new FormControl();
//   users: User[] = [];
//   filteredUsers: User[] = [];

//   constructor(private userService: UserService, private elementRef: ElementRef) { }

//   ngOnInit(): void {
//     this.getUsers();
//     this.observerChangeSearch();
//     this.filteredUsers = [];
//   }

//   ngOnAfterViewInit(): void {
//     this.observerChangeSearch();
//   }

//   observerChangeSearch() {
//     this.control.valueChanges
//       .pipe(debounceTime(700))
//       .subscribe(value => {
//         value = value.toLowerCase();
//         //filtramos los usuarios que empiecen por lo que se ha escrito
//         this.filteredUsers = this.users.filter(user =>
//           user.username.toLowerCase().startsWith(value) || user.name.toLowerCase().startsWith(value)
//           || user.lastname.toLowerCase().startsWith(value)
//         );
//         //para que cuando no haya nada escrito, no se muestren todos, ya que todos empiezan por ""
//         if (value == "" || value == null) {
//           this.filteredUsers = [];
//         }
//         console.log('Resultados de búsqueda:', this.filteredUsers);
//       });
//   }

//   getUsers() {
//     this.userService.getUsersList().subscribe(data => {
//       this.users = data;
//     })
//   }

//   //para que cuando clicke en el inputSearch se cambien los estilos.
//   onFocus() {
//     document.getElementById('navbar')?.classList.add('focused');
//     document.getElementById('i')?.classList.remove('text-secondary');
//     document.getElementById('i')?.classList.add('text-primary');
//   }
//   onBlur() {
//     document.getElementById('navbar')?.classList.remove('focused');
//     document.getElementById('i')?.classList.remove('text-primary');
//   }

// }
