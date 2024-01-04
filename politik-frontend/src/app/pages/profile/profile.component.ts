import { UserService } from 'src/app/services/user/user.service';
import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  tabs: string[] = ['Posts', 'Polls', 'Reposts', 'LifeBook'];

  activatedTab: number = 0; //paso esta variable con el index para saber que tab esta activo

  public username: string = '';
  public name: string = '';
  public lastname: string = '';
  public birthday: string = '';

  //para la searchBar
  users: User[] = [];
  filteredUsers: User[] = [];

  constructor(private userService: UserService, private datePipe: DatePipe) { }

  ngOnInit() {
    this.getUsers(); //traigo todos los users para la searchBar
    this.filteredUsers = [];
    const user = this.userService.getCurrentUser();
    if (user) {
      this.username = user.username;
      this.name = user.name;
      this.lastname = user.lastname;
      if (user.birthday) {
        this.birthday = this.datePipe.transform(user.birthday, 'MMMM d, y') || '';
      }
    }
  }

  observerChangeSearch(value: string) {
    value = value.toLowerCase();
    this.filteredUsers = this.users.filter(user =>
      user.username.toLowerCase().startsWith(value) || user.name.toLowerCase().startsWith(value) ||
      user.lastname.toLowerCase().startsWith(value)
    );
    if(value == '' || value == null){
      this.filteredUsers = [];
    }
  }

  getUsers(){
    this.userService.getUsersList().subscribe(data =>
      this.users = data
    )
  }

  setTab(index: number) {
    this.activatedTab = index;
  }
}
