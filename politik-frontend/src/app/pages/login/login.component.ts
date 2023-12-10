import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ForgotPasswordComponent } from '../forgot-password/forgot-password.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  showSecondPart = false; //sí no existe un usuario con ese phone, email or username

  constructor(private dialog: MatDialog, private router: Router) {}

  closeDialog(){
    this.dialog.closeAll();
  }

  checkUser(){
    //falta hacer la petición al backend para checkear si existe
    this.showSecondPart = true;
  }

  openForgotPassword(){
    this.closeDialog();
    this.dialog.open(ForgotPasswordComponent, {
      width: '40%',
      height: '90%'
    });
  }

  checkPassword(){
    //falta hacer la petición al backend para checkear si la contraseña es correcta
    this.closeDialog();
    this.router.navigate(['/home']);
  }
}
