import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HomeComponent } from './home/home.component';
import { LandingComponent } from './landing/landing.component';
import { SignupComponent } from './signup/signup.component';
import { StartComponent } from './start/start.component';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';


@NgModule({
  declarations: [
    HomeComponent,
    LandingComponent,
    SignupComponent,
    StartComponent,
    LoginComponent,
    ForgotPasswordComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    HomeComponent,
    LandingComponent,
    SignupComponent,
    StartComponent,
    LoginComponent,
  ]
})
export class PagesModule { }
