import { UserService } from 'src/app/services/user/user.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {

  public username: string = '';
  public name: string = '';
  public lastname: string = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    const user = this.userService.getCurrentUser();
    if (user) {
      this.username = user.username;
      this.name = user.name;
      this.lastname = user.lastname;
    }
  }

  logout() {
    this.userService.logout();
  }
}
