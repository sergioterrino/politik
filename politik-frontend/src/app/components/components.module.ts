import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListOfUsersComponent } from './list-of-users/list-of-users.component';



@NgModule({
  declarations: [
    ListOfUsersComponent
  ],
  imports: [
    CommonModule,
  ],
  exports: [
    ListOfUsersComponent
  ]
})
export class ComponentsModule { }
