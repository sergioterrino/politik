import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListOfUsersComponent } from './list-of-users/list-of-users.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { SuggestionsComponent } from './suggestions/suggestions.component';
import { RouterModule } from '@angular/router';
import { PostsProfileComponent } from './posts-profile/posts-profile.component';
import { PollsProfileComponent } from './polls-profile/polls-profile.component';
import { RepostsProfileComponent } from './reposts-profile/reposts-profile.component';
import { PostComponent } from './post/post.component';


@NgModule({
  declarations: [
    ListOfUsersComponent,
    SideBarComponent,
    SearchBarComponent,
    SuggestionsComponent,
    PostsProfileComponent,
    PollsProfileComponent,
    RepostsProfileComponent,
    PostComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    ListOfUsersComponent,
    SideBarComponent,
    SearchBarComponent,
    SuggestionsComponent,
    PostsProfileComponent,
    PollsProfileComponent,
    RepostsProfileComponent,
  ]
})
export class ComponentsModule { }
