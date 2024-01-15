import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListOfUsersComponent } from './components/list-of-users/list-of-users.component';
import { LandingComponent } from './pages/landing/landing.component';
import { SignupComponent } from './pages/signup/signup.component';
import { StartComponent } from './pages/start/start.component';
import { HomeComponent } from './pages/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ExploreComponent } from './pages/explore/explore.component';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: '/start', pathMatch: 'full' },
  { path: 'start', component: StartComponent },
  { path: 'landing', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomeComponent },
  { path: 'explore', component: ExploreComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'users', component: ListOfUsersComponent },
  { path: 'list', component: ListOfUsersComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
