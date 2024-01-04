import { UserService } from 'src/app/services/user/user.service';
import { Component } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  users: User[] = [];
  filteredUsers: User[] = [];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getUsers();
    this.filteredUsers = [];
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



  adjustTextarea(event: any): void {
    let textarea: any = event.target;
    textarea.style.overflow = 'hidden';
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
  }
}
